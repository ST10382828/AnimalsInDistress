package student.projects.animalsindistress.ui.fragments

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import student.projects.animalsindistress.R
import student.projects.animalsindistress.data.MedicalAidRequest
import java.io.File
import java.util.Locale

class ServicesFragment : Fragment(R.layout.fragment_services) {
    private var photoUri: Uri? = null
    private var currentPhotoFile: File? = null
    private var capturedLatitude: Double? = null
    private var capturedLongitude: Double? = null
    private var dialogView: View? = null

    private val requestLocationPermission = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
        if (permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true || 
            permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true) {
            dialogView?.findViewById<EditText>(R.id.input_location)?.let { etLocation ->
                fetchLocation(etLocation)
            }
        } else {
            Toast.makeText(requireContext(), "Location permission needed to auto-fill address", Toast.LENGTH_SHORT).show()
        }
    }

    private val requestCameraPermission = registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
        if (granted) {
            openCamera()
        } else {
            Toast.makeText(requireContext(), "Camera permission needed to take photo", Toast.LENGTH_SHORT).show()
        }
    }

    private val cameraLauncher = registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
        if (success && photoUri != null && dialogView != null) {
            dialogView?.findViewById<ImageView>(R.id.iv_photo)?.apply {
                setImageURI(photoUri)
                visibility = View.VISIBLE
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Call emergency line
        view.findViewById<View>(R.id.btn_call_emergency)?.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:0114660261"))
            startActivity(intent)
        }

        // Request medical aid
        view.findViewById<View>(R.id.btn_request_medical_aid)?.setOnClickListener {
            showMedicalAidDialog()
        }

        // Request mobile visit
        view.findViewById<View>(R.id.btn_request_mobile_visit)?.setOnClickListener {
            showMedicalAidDialog()
        }
    }

    private fun showMedicalAidDialog() {
        dialogView = LayoutInflater.from(requireContext())
            .inflate(R.layout.dialog_request_medical_aid, null, false)
        val dialog = MaterialAlertDialogBuilder(requireContext())
            .setView(dialogView)
            .create()

        // Reset photo state
        photoUri = null
        currentPhotoFile = null
        capturedLatitude = null
        capturedLongitude = null

        val etName = dialogView?.findViewById<EditText>(R.id.input_name)
        val etPhone = dialogView?.findViewById<EditText>(R.id.input_phone)
        val etLocation = dialogView?.findViewById<EditText>(R.id.input_location)
        val etDescription = dialogView?.findViewById<EditText>(R.id.input_description)
        val btnGetLocation = dialogView?.findViewById<Button>(R.id.btn_get_location)
        val btnTakePhoto = dialogView?.findViewById<Button>(R.id.btn_take_photo)
        val ivPhoto = dialogView?.findViewById<ImageView>(R.id.iv_photo)
        val btnCancel = dialogView?.findViewById<Button>(R.id.btn_cancel)
        val btnSubmit = dialogView?.findViewById<Button>(R.id.btn_submit)

        // Pre-fill user info if logged in
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            etName?.setText(user.displayName ?: "")
            etPhone?.setText(user.phoneNumber ?: "")
        }

        // Auto-fetch location on dialog open (after a short delay to ensure dialog is shown)
        dialog.setOnShowListener {
            btnGetLocation?.performClick()
        }

        btnGetLocation?.setOnClickListener {
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                fetchLocation(etLocation)
            } else {
                requestLocationPermission.launch(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION))
            }
        }

        btnTakePhoto?.setOnClickListener {
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                openCamera()
            } else {
                requestCameraPermission.launch(Manifest.permission.CAMERA)
            }
        }

        btnCancel?.setOnClickListener { 
            dialog.dismiss()
            dialogView = null
        }

        btnSubmit?.setOnClickListener {
            val name = etName?.text?.toString()?.trim() ?: ""
            val phone = etPhone?.text?.toString()?.trim() ?: ""
            val location = etLocation?.text?.toString()?.trim() ?: ""
            val description = etDescription?.text?.toString()?.trim() ?: ""

            if (name.isEmpty() || phone.isEmpty() || location.isEmpty() || description.isEmpty()) {
                Toast.makeText(requireContext(), "Please fill in all required fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            submitMedicalAidRequest(name, phone, location, description, photoUri, dialog)
        }

        dialog.setOnDismissListener {
            dialogView = null
        }

        dialog.show()
    }

    private fun fetchLocation(etLocation: EditText? = null) {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return
        }

        try {
            val locationManager = requireContext().getSystemService(android.content.Context.LOCATION_SERVICE) as? android.location.LocationManager
            val location = locationManager?.getLastKnownLocation(android.location.LocationManager.GPS_PROVIDER)
                ?: locationManager?.getLastKnownLocation(android.location.LocationManager.NETWORK_PROVIDER)

            if (location != null) {
                capturedLatitude = location.latitude
                capturedLongitude = location.longitude
                try {
                    val geocoder = Geocoder(requireContext(), Locale.getDefault())
                    val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
                    if (!addresses.isNullOrEmpty()) {
                        val address = addresses[0]
                        val addressText = address.getAddressLine(0) ?: "${address.latitude}, ${address.longitude}"
                        etLocation?.setText(addressText)
                    }
                } catch (e: Exception) {
                    etLocation?.setText("${location.latitude}, ${location.longitude}")
                }
            } else {
                Toast.makeText(requireContext(), "Could not get location. Please enter manually.", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            Toast.makeText(requireContext(), "Location unavailable. Please enter manually.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun openCamera() {
        try {
            val photoFile = File.createTempFile("medical_aid_", ".jpg", requireContext().cacheDir)
            currentPhotoFile = photoFile
            photoUri = FileProvider.getUriForFile(
                requireContext(),
                "${requireContext().packageName}.fileprovider",
                photoFile
            )
            cameraLauncher.launch(photoUri)
        } catch (e: Exception) {
            Toast.makeText(requireContext(), "Could not open camera", Toast.LENGTH_SHORT).show()
        }
    }

    private fun submitMedicalAidRequest(name: String, phone: String, location: String, description: String, photoUri: Uri?, dialog: androidx.appcompat.app.AlertDialog) {
        val user = FirebaseAuth.getInstance().currentUser
        val userId = user?.uid ?: ""
        val userEmail = user?.email ?: ""

        // Upload photo first if exists
        if (photoUri != null) {
            val storageRef = Firebase.storage.reference.child("medical_aid/${System.currentTimeMillis()}.jpg")
            storageRef.putFile(photoUri)
                .continueWithTask { task ->
                    if (task.isSuccessful) storageRef.downloadUrl else null
                }
                .addOnSuccessListener { imageUrl ->
                    saveRequest(name, phone, location, description, imageUrl.toString(), dialog)
                }
                .addOnFailureListener {
                    saveRequest(name, phone, location, description, null, dialog)
                }
        } else {
            saveRequest(name, phone, location, description, null, dialog)
        }
    }

    private fun saveRequest(name: String, phone: String, location: String, description: String, imageUrl: String?, dialog: androidx.appcompat.app.AlertDialog) {
        val user = FirebaseAuth.getInstance().currentUser
        val request = MedicalAidRequest(
            id = "",
            userId = user?.uid ?: "",
            userName = name,
            userEmail = user?.email ?: "",
            phone = phone,
            location = location,
            latitude = capturedLatitude,
            longitude = capturedLongitude,
            description = description,
            imageUrl = imageUrl,
            timestamp = Timestamp.now(),
            status = "pending"
        )

        Firebase.firestore.collection("medical_aid_requests")
            .add(request)
            .addOnSuccessListener { docRef ->
                // Notify all admins
                notifyAdmins(docRef.id)
                dialog.dismiss()
                Toast.makeText(requireContext(), "Medical aid request submitted", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), "Failed to submit request", Toast.LENGTH_SHORT).show()
            }
    }

    private fun notifyAdmins(requestId: String) {
        Firebase.firestore.collection("users")
            .whereEqualTo("role", "admin")
            .get()
            .addOnSuccessListener { snapshot ->
                snapshot.documents.forEach { adminDoc ->
                    val adminUid = adminDoc.id
                    val notificationManager = requireContext().getSystemService(android.content.Context.NOTIFICATION_SERVICE) as? android.app.NotificationManager
                    val intent = Intent(requireContext(), student.projects.animalsindistress.MainActivity::class.java).apply {
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        putExtra("medical_aid_request_id", requestId)
                    }
                    val pendingIntent = android.app.PendingIntent.getActivity(
                        requireContext(),
                        requestId.hashCode(),
                        intent,
                        android.app.PendingIntent.FLAG_IMMUTABLE
                    )

                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                        val channel = android.app.NotificationChannel(
                            "medical_aid_requests",
                            "Medical Aid Requests",
                            android.app.NotificationManager.IMPORTANCE_HIGH
                        )
                        notificationManager?.createNotificationChannel(channel)
                    }

                    val notification = androidx.core.app.NotificationCompat.Builder(requireContext(), "medical_aid_requests")
                        .setSmallIcon(android.R.drawable.ic_dialog_info)
                        .setContentTitle("New Medical Aid Request")
                        .setContentText("A new medical aid request needs your attention")
                        .setPriority(androidx.core.app.NotificationCompat.PRIORITY_HIGH)
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true)
                        .build()

                    notificationManager?.notify(adminUid.hashCode(), notification)
                }
            }
    }
}



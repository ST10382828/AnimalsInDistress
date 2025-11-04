package student.projects.animalsindistress.ui.fragments.admin

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import student.projects.animalsindistress.R
import student.projects.animalsindistress.data.MedicalAidRequest
import java.text.SimpleDateFormat
import java.util.*

class AdminMedicalAidRequestsFragment : Fragment(R.layout.fragment_admin_medical_aid_requests) {

    private val db = Firebase.firestore
    private val requests = mutableListOf<MedicalAidRequest>()
    private lateinit var adapter: RequestsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Check if user is admin
        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser == null) {
            Toast.makeText(requireContext(), "Please log in first", Toast.LENGTH_SHORT).show()
            return
        }

        db.collection("users").document(currentUser.uid).get()
            .addOnSuccessListener { doc ->
                val role = doc.getString("role") ?: "user"
                if (role != "admin") {
                    Toast.makeText(requireContext(), "Admin access required", Toast.LENGTH_SHORT).show()
                    return@addOnSuccessListener
                }
                loadRequests()
            }

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewRequests)
        adapter = RequestsAdapter(requests) { request, action ->
            when (action) {
                "view" -> showRequestDetails(request)
                "updateStatus" -> updateStatus(request)
                "delete" -> deleteRequest(request)
            }
        }
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        // Check if we came from a notification
        val requestId = arguments?.getString("medical_aid_request_id")
        if (requestId != null) {
            db.collection("medical_aid_requests").document(requestId).get()
                .addOnSuccessListener { doc ->
                    if (doc.exists()) {
                        val request = doc.toObject(MedicalAidRequest::class.java)?.copy(id = doc.id)
                        if (request != null) {
                            showRequestDetails(request)
                        }
                    }
                }
        }
    }

    private fun loadRequests() {
        db.collection("medical_aid_requests")
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { result ->
                requests.clear()
                for (document in result) {
                    val request = document.toObject(MedicalAidRequest::class.java).copy(id = document.id)
                    requests.add(request)
                }
                adapter.notifyDataSetChanged()
                updateEmptyState()
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), "Failed to load requests", Toast.LENGTH_SHORT).show()
            }
    }

    private fun showRequestDetails(request: MedicalAidRequest) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_medical_aid_request_details, null)
        
        dialogView.findViewById<TextView>(R.id.tvRequestName).text = request.userName
        dialogView.findViewById<TextView>(R.id.tvRequestEmail).text = request.userEmail
        dialogView.findViewById<TextView>(R.id.tvRequestPhone).text = request.phone
        dialogView.findViewById<TextView>(R.id.tvRequestLocation).text = request.location
        dialogView.findViewById<TextView>(R.id.tvRequestDescription).text = request.description
        dialogView.findViewById<TextView>(R.id.tvRequestStatus).text = request.status.replaceFirstChar { it.uppercaseChar() }
        dialogView.findViewById<TextView>(R.id.tvRequestDate).text = SimpleDateFormat("MMM d, yyyy 'at' h:mm a", Locale.getDefault()).format(request.timestamp.toDate())
        
        val imageView = dialogView.findViewById<ImageView>(R.id.ivRequestPhoto)
        if (!request.imageUrl.isNullOrEmpty()) {
            imageView.visibility = View.VISIBLE
            Glide.with(requireContext())
                .load(request.imageUrl)
                .into(imageView)
        } else {
            imageView.visibility = View.GONE
        }

        val notesView = dialogView.findViewById<TextView>(R.id.etAdminNotes)
        notesView.text = request.adminNotes

        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Medical Aid Request Details")
            .setView(dialogView)
            .setPositiveButton("Close", null)
            .setNeutralButton("Update Status") { _, _ ->
                updateStatus(request)
            }
            .show()
    }

    private fun updateStatus(request: MedicalAidRequest) {
        val statuses = arrayOf("pending", "in_progress", "completed")
        val currentIndex = statuses.indexOf(request.status)
        
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Update Status")
            .setSingleChoiceItems(statuses.map { it.replace("_", " ").replaceFirstChar { char -> char.uppercaseChar() } }.toTypedArray(), currentIndex) { dialog, which ->
                val newStatus = statuses[which]
                db.collection("medical_aid_requests").document(request.id)
                    .update("status", newStatus)
                    .addOnSuccessListener {
                        val index = requests.indexOfFirst { it.id == request.id }
                        if (index != -1) {
                            requests[index] = request.copy(status = newStatus)
                            adapter.notifyItemChanged(index)
                        }
                        Toast.makeText(requireContext(), "Status updated", Toast.LENGTH_SHORT).show()
                        dialog.dismiss()
                    }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun deleteRequest(request: MedicalAidRequest) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Delete Request")
            .setMessage("Are you sure you want to delete this medical aid request?")
            .setPositiveButton("Delete") { _, _ ->
                db.collection("medical_aid_requests").document(request.id)
                    .delete()
                    .addOnSuccessListener {
                        val index = requests.indexOfFirst { it.id == request.id }
                        if (index != -1) {
                            requests.removeAt(index)
                            adapter.notifyItemRemoved(index)
                        }
                        updateEmptyState()
                        Toast.makeText(requireContext(), "Deleted", Toast.LENGTH_SHORT).show()
                    }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun updateEmptyState() {
        view?.findViewById<TextView>(R.id.tvEmptyState)?.visibility =
            if (requests.isEmpty()) View.VISIBLE else View.GONE
        view?.findViewById<RecyclerView>(R.id.recyclerViewRequests)?.visibility =
            if (requests.isEmpty()) View.GONE else View.VISIBLE
    }
}

class RequestsAdapter(
    private val requests: List<MedicalAidRequest>,
    private val onAction: (MedicalAidRequest, String) -> Unit
) : RecyclerView.Adapter<RequestsAdapter.ViewHolder>() {

    private val dateFormat = SimpleDateFormat("MMM d, yyyy 'at' h:mm a", Locale.getDefault())

    override fun onCreateViewHolder(parent: android.view.ViewGroup, viewType: Int): ViewHolder {
        val view = android.view.LayoutInflater.from(parent.context)
            .inflate(R.layout.item_medical_aid_request, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(requests[position])
    }

    override fun getItemCount() = requests.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tvName: TextView = view.findViewById(R.id.tvRequestName)
        private val tvLocation: TextView = view.findViewById(R.id.tvRequestLocation)
        private val tvStatus: TextView = view.findViewById(R.id.tvRequestStatus)
        private val tvDate: TextView = view.findViewById(R.id.tvRequestDate)
        private val statusIndicator: View = view.findViewById(R.id.statusIndicator)
        private val btnView: Button = view.findViewById(R.id.btnView)
        private val btnDelete: Button = view.findViewById(R.id.btnDelete)

        fun bind(request: MedicalAidRequest) {
            tvName.text = request.userName
            tvLocation.text = request.location
            tvStatus.text = request.status.replace("_", " ").replaceFirstChar { it.uppercaseChar() }
            tvDate.text = dateFormat.format(request.timestamp.toDate())
            
            // Set status indicator color
            val statusColor = when (request.status) {
                "pending" -> 0xFFFF9800.toInt() // Orange
                "in_progress" -> 0xFF2196F3.toInt() // Blue
                "completed" -> 0xFF4CAF50.toInt() // Green
                else -> 0xFF757575.toInt() // Gray
            }
            statusIndicator.setBackgroundColor(statusColor)

            btnView.setOnClickListener { onAction(request, "view") }
            btnDelete.setOnClickListener { onAction(request, "delete") }
        }
    }
}


package student.projects.animalsindistress.ui.fragments.admin

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Base64
import android.view.View
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import student.projects.animalsindistress.R
import student.projects.animalsindistress.data.GalleryImage
import java.io.ByteArrayOutputStream

class AdminGalleryEditorFragment : Fragment(R.layout.fragment_admin_gallery_editor) {

    private val db = Firebase.firestore
    private lateinit var adapter: GalleryImagesAdapter
    private val galleryImages = mutableListOf<GalleryImage>()
    
    private var selectedImageBase64: String? = null
    private var currentPreviewImageView: ImageView? = null

    private val imagePickerLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.data?.let { uri ->
                selectedImageBase64 = convertImageToBase64(uri)
                // Update the preview ImageView
                selectedImageBase64?.let { base64 ->
                    val base64String = base64.substringAfter("base64,")
                    val imageBytes = Base64.decode(base64String, Base64.DEFAULT)
                    val bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
                    currentPreviewImageView?.setImageBitmap(bitmap)
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view_gallery_images)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = GalleryImagesAdapter(
            onEdit = { image -> showEditDialog(image) },
            onDelete = { image -> deleteImage(image) }
        )
        recyclerView.adapter = adapter

        view.findViewById<MaterialButton>(R.id.btn_add_image)?.setOnClickListener {
            showAddDialog()
        }

        loadGalleryImages()
    }

    private fun loadGalleryImages() {
        db.collection("gallery_images")
            .orderBy("timestamp", com.google.firebase.firestore.Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    Toast.makeText(requireContext(), "Error loading images", Toast.LENGTH_SHORT).show()
                    return@addSnapshotListener
                }

                galleryImages.clear()
                snapshot?.documents?.forEach { doc ->
                    val image = doc.toObject(GalleryImage::class.java)?.copy(id = doc.id)
                    image?.let { galleryImages.add(it) }
                }
                adapter.submitList(galleryImages)
            }
    }

    private fun showAddDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_add_gallery_image, null)
        val etTitle = dialogView.findViewById<EditText>(R.id.et_title)
        val etDescription = dialogView.findViewById<EditText>(R.id.et_description)
        val spinnerCategory = dialogView.findViewById<Spinner>(R.id.spinner_category)
        val btnSelectImage = dialogView.findViewById<MaterialButton>(R.id.btn_select_image)
        val ivPreview = dialogView.findViewById<ImageView>(R.id.iv_preview)

        // Setup category spinner
        val categories = arrayOf("all", "memory", "animals", "volunteers", "diamonds", "horses", "events")
        spinnerCategory.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, categories).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }

        selectedImageBase64 = null
        currentPreviewImageView = ivPreview
        
        btnSelectImage.setOnClickListener {
            android.util.Log.d("AdminGalleryEditor", "Select Image button clicked")
            try {
                val intent = android.content.Intent(android.content.Intent.ACTION_PICK).apply {
                    type = "image/*"
                }
                android.util.Log.d("AdminGalleryEditor", "Launching image picker")
                imagePickerLauncher.launch(intent)
            } catch (e: Exception) {
                android.util.Log.e("AdminGalleryEditor", "Error launching image picker", e)
                Toast.makeText(requireContext(), "Error opening image picker: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }

        val dialog = MaterialAlertDialogBuilder(requireContext())
            .setTitle("Add Gallery Image")
            .setView(dialogView)
            .setPositiveButton("Add") { _, _ ->
                val title = etTitle.text.toString().trim()
                val description = etDescription.text.toString().trim()
                val category = spinnerCategory.selectedItem.toString()
                val imageUrl = selectedImageBase64

                if (imageUrl.isNullOrEmpty()) {
                    Toast.makeText(requireContext(), "Please select an image first", Toast.LENGTH_SHORT).show()
                    showAddDialog() // Re-show the dialog
                    return@setPositiveButton
                }

                addGalleryImage(title, description, category, imageUrl)
                currentPreviewImageView = null
            }
            .setNegativeButton("Cancel") { _, _ ->
                currentPreviewImageView = null
            }
            .create()
        
        dialog.show()
    }

    private fun showEditDialog(image: GalleryImage) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_add_gallery_image, null)
        val etTitle = dialogView.findViewById<EditText>(R.id.et_title)
        val etDescription = dialogView.findViewById<EditText>(R.id.et_description)
        val spinnerCategory = dialogView.findViewById<Spinner>(R.id.spinner_category)
        val btnSelectImage = dialogView.findViewById<MaterialButton>(R.id.btn_select_image)
        val ivPreview = dialogView.findViewById<ImageView>(R.id.iv_preview)

        // Pre-fill data
        etTitle.setText(image.title)
        etDescription.setText(image.description)

        val categories = arrayOf("all", "memory", "animals", "volunteers", "diamonds", "horses", "events")
        spinnerCategory.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, categories).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
        spinnerCategory.setSelection(categories.indexOf(image.category).coerceAtLeast(0))

        // Load existing image
        if (image.imageUrl.startsWith("data:image")) {
            val base64String = image.imageUrl.substringAfter("base64,")
            val imageBytes = Base64.decode(base64String, Base64.DEFAULT)
            val bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
            ivPreview.setImageBitmap(bitmap)
        } else {
            ivPreview.load(image.imageUrl)
        }

        selectedImageBase64 = image.imageUrl
        currentPreviewImageView = ivPreview
        btnSelectImage.text = "Change Image"
        
        btnSelectImage.setOnClickListener {
            val intent = android.content.Intent(android.content.Intent.ACTION_PICK).apply {
                type = "image/*"
            }
            imagePickerLauncher.launch(intent)
        }

        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Edit Gallery Image")
            .setView(dialogView)
            .setPositiveButton("Update") { _, _ ->
                val title = etTitle.text.toString().trim()
                val description = etDescription.text.toString().trim()
                val category = spinnerCategory.selectedItem.toString()

                updateGalleryImage(image.id, title, description, category, selectedImageBase64 ?: image.imageUrl)
                currentPreviewImageView = null
            }
            .setNegativeButton("Cancel") { _, _ ->
                currentPreviewImageView = null
            }
            .show()
    }

    private fun addGalleryImage(title: String, description: String, category: String, imageUrl: String) {
        val currentUser = FirebaseAuth.getInstance().currentUser ?: return

        val newImage = GalleryImage(
            title = title,
            description = description,
            category = category,
            imageUrl = imageUrl,
            timestamp = Timestamp.now(),
            addedBy = currentUser.uid
        )

        db.collection("gallery_images")
            .add(newImage)
            .addOnSuccessListener {
                Toast.makeText(requireContext(), "Image added successfully", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), "Failed to add image", Toast.LENGTH_SHORT).show()
            }
    }

    private fun updateGalleryImage(id: String, title: String, description: String, category: String, imageUrl: String) {
        val updates = mapOf(
            "title" to title,
            "description" to description,
            "category" to category,
            "imageUrl" to imageUrl
        )

        db.collection("gallery_images")
            .document(id)
            .update(updates)
            .addOnSuccessListener {
                Toast.makeText(requireContext(), "Image updated successfully", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), "Failed to update image", Toast.LENGTH_SHORT).show()
            }
    }

    private fun deleteImage(image: GalleryImage) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Delete Image")
            .setMessage("Are you sure you want to delete this image?")
            .setPositiveButton("Delete") { _, _ ->
                db.collection("gallery_images")
                    .document(image.id)
                    .delete()
                    .addOnSuccessListener {
                        Toast.makeText(requireContext(), "Image deleted", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener {
                        Toast.makeText(requireContext(), "Failed to delete image", Toast.LENGTH_SHORT).show()
                    }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun convertImageToBase64(uri: Uri): String {
        val inputStream = requireContext().contentResolver.openInputStream(uri) ?: return ""
        val bitmap = BitmapFactory.decodeStream(inputStream)
        
        // Compress to max 800px width/height
        val maxSize = 800
        val ratio = Math.min(
            maxSize.toFloat() / bitmap.width,
            maxSize.toFloat() / bitmap.height
        )
        val width = (bitmap.width * ratio).toInt()
        val height = (bitmap.height * ratio).toInt()
        val resizedBitmap = Bitmap.createScaledBitmap(bitmap, width, height, true)
        
        val outputStream = ByteArrayOutputStream()
        resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 80, outputStream)
        val byteArray = outputStream.toByteArray()
        val base64String = Base64.encodeToString(byteArray, Base64.DEFAULT)
        
        return "data:image/jpeg;base64,$base64String"
    }

    private class GalleryImagesAdapter(
        private val onEdit: (GalleryImage) -> Unit,
        private val onDelete: (GalleryImage) -> Unit
    ) : RecyclerView.Adapter<GalleryImagesAdapter.ViewHolder>() {

        private val images = mutableListOf<GalleryImage>()

        fun submitList(newImages: List<GalleryImage>) {
            images.clear()
            images.addAll(newImages)
            notifyDataSetChanged()
        }

        override fun onCreateViewHolder(parent: android.view.ViewGroup, viewType: Int): ViewHolder {
            val view = android.view.LayoutInflater.from(parent.context)
                .inflate(R.layout.item_admin_gallery_image, parent, false)
            return ViewHolder(view)
        }

        override fun getItemCount() = images.size

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(images[position], onEdit, onDelete)
        }

        class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            private val ivImage = itemView.findViewById<ImageView>(R.id.iv_image)
            private val tvTitle = itemView.findViewById<TextView>(R.id.tv_title)
            private val tvCategory = itemView.findViewById<TextView>(R.id.tv_category)
            private val btnEdit = itemView.findViewById<MaterialButton>(R.id.btn_edit)
            private val btnDelete = itemView.findViewById<MaterialButton>(R.id.btn_delete)

            fun bind(image: GalleryImage, onEdit: (GalleryImage) -> Unit, onDelete: (GalleryImage) -> Unit) {
                tvTitle.text = image.title.ifEmpty { "Untitled" }
                tvCategory.text = image.category

                // Load image (Base64 or URL)
                if (image.imageUrl.startsWith("data:image")) {
                    val base64String = image.imageUrl.substringAfter("base64,")
                    val imageBytes = Base64.decode(base64String, Base64.DEFAULT)
                    val bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
                    ivImage.setImageBitmap(bitmap)
                } else {
                    ivImage.load(image.imageUrl)
                }

                btnEdit.setOnClickListener { onEdit(image) }
                btnDelete.setOnClickListener { onDelete(image) }
            }
        }
    }
}

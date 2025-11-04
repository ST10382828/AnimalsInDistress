package student.projects.animalsindistress.ui.fragments.admin

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import student.projects.animalsindistress.R
import student.projects.animalsindistress.data.models.Story
import student.projects.animalsindistress.data.models.MediaItem
import student.projects.animalsindistress.data.models.MediaType
import java.io.ByteArrayOutputStream
import java.util.UUID

class AdminStoriesEditorFragment : Fragment(R.layout.fragment_admin_stories_editor) {

    private val db = Firebase.firestore
    private val stories = mutableListOf<Story>()
    private lateinit var adapter: StoryEditorAdapter
    private var currentEditingStory: Story? = null
    private val selectedImageUris = mutableListOf<Uri>()

    private lateinit var etStoryId: TextInputEditText
    private lateinit var etTitle: TextInputEditText
    private lateinit var etSummary: TextInputEditText
    private lateinit var etFullContent: TextInputEditText
    private lateinit var etAdoptedUpdate: TextInputEditText
    private lateinit var etMediaUrls: TextInputEditText
    private lateinit var btnSaveStory: Button
    private lateinit var btnSelectImages: Button
    private lateinit var selectedImagesContainer: LinearLayout
    
    private val imagePickerLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.let { data ->
                // Handle multiple image selection
                if (data.clipData != null) {
                    val count = data.clipData!!.itemCount
                    for (i in 0 until count) {
                        val imageUri = data.clipData!!.getItemAt(i).uri
                        selectedImageUris.add(imageUri)
                    }
                } else if (data.data != null) {
                    // Single image selected
                    selectedImageUris.add(data.data!!)
                }
                displaySelectedImages()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Check if user is admin
        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser == null) {
            Toast.makeText(requireContext(), "Please login first", Toast.LENGTH_SHORT).show()
            return
        }

        // Initialize views
        etStoryId = view.findViewById(R.id.etStoryId)
        etTitle = view.findViewById(R.id.etTitle)
        etSummary = view.findViewById(R.id.etSummary)
        etFullContent = view.findViewById(R.id.etFullContent)
        etAdoptedUpdate = view.findViewById(R.id.etAdoptedUpdate)
        etMediaUrls = view.findViewById(R.id.etMediaUrls)
        btnSaveStory = view.findViewById(R.id.btnSaveStory)
        btnSelectImages = view.findViewById(R.id.btnSelectImages)
        selectedImagesContainer = view.findViewById(R.id.selectedImagesContainer)

        // Setup RecyclerView
        adapter = StoryEditorAdapter(stories) { story, action ->
            when (action) {
                "edit" -> loadStoryToForm(story)
                "delete" -> deleteStory(story)
            }
        }

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewStories)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        // Setup save button
        btnSaveStory.setOnClickListener {
            saveStory()
        }
        
        // Setup select images button
        btnSelectImages.setOnClickListener {
            openImagePicker()
        }
        
        // Setup clear button
        view.findViewById<Button>(R.id.btnClearForm)?.setOnClickListener {
            clearForm()
        }

        // Load existing stories
        loadStories()
    }
    
    private fun openImagePicker() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI).apply {
            putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            type = "image/*"
        }
        imagePickerLauncher.launch(intent)
    }
    
    private fun displaySelectedImages() {
        selectedImagesContainer.removeAllViews()
        selectedImageUris.forEachIndexed { index, uri ->
            val imageView = ImageView(requireContext()).apply {
                layoutParams = LinearLayout.LayoutParams(200, 200).apply {
                    setMargins(8, 8, 8, 8)
                }
                scaleType = ImageView.ScaleType.CENTER_CROP
                load(uri)
                setOnClickListener {
                    // Remove image on click
                    selectedImageUris.removeAt(index)
                    displaySelectedImages()
                }
            }
            selectedImagesContainer.addView(imageView)
        }
        
        // Update info text
        val infoText = if (selectedImageUris.isEmpty()) {
            "No images selected"
        } else {
            "${selectedImageUris.size} image(s) selected (tap to remove)"
        }
        Toast.makeText(requireContext(), infoText, Toast.LENGTH_SHORT).show()
    }
    
    private suspend fun uploadImagesToFirebase(): List<String> {
        val base64Images = mutableListOf<String>()
        
        selectedImageUris.forEachIndexed { index, uri ->
            try {
                android.util.Log.d("AdminStoriesEditor", "Processing image ${index + 1}/${selectedImageUris.size}")
                
                // Convert URI to Base64 on background thread
                val base64String = withContext(Dispatchers.IO) {
                    val inputStream = requireContext().contentResolver.openInputStream(uri)
                    val bitmap = BitmapFactory.decodeStream(inputStream)
                    inputStream?.close()
                    
                    // Compress image to reduce size (max width 800px)
                    val scaledBitmap = if (bitmap.width > 800) {
                        val ratio = 800.0 / bitmap.width
                        val newHeight = (bitmap.height * ratio).toInt()
                        Bitmap.createScaledBitmap(bitmap, 800, newHeight, true)
                    } else {
                        bitmap
                    }
                    
                    // Convert to Base64
                    val outputStream = ByteArrayOutputStream()
                    scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 80, outputStream)
                    val byteArray = outputStream.toByteArray()
                    
                    bitmap.recycle()
                    if (scaledBitmap != bitmap) scaledBitmap.recycle()
                    
                    "data:image/jpeg;base64," + Base64.encodeToString(byteArray, Base64.DEFAULT)
                }
                
                base64Images.add(base64String)
                android.util.Log.d("AdminStoriesEditor", "Successfully converted image ${index + 1} to Base64")
            } catch (e: Exception) {
                android.util.Log.e("AdminStoriesEditor", "Conversion failed for image ${index + 1}: ${e.message}", e)
                requireActivity().runOnUiThread {
                    Toast.makeText(
                        requireContext(), 
                        "Failed to process image ${index + 1}: ${e.message}", 
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
        
        return base64Images
    }

    private fun loadStories() {
        db.collection("stories")
            .get()
            .addOnSuccessListener { documents ->
                stories.clear()
                for (doc in documents) {
                    val story = doc.toObject(Story::class.java)
                    stories.add(story)
                }
                adapter.notifyDataSetChanged()
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), "Failed to load stories", Toast.LENGTH_SHORT).show()
            }
    }

    private fun saveStory() {
        val title = etTitle.text?.toString()?.trim()
        val summary = etSummary.text?.toString()?.trim()
        val fullContent = etFullContent.text?.toString()?.trim()

        if (title.isNullOrEmpty() || summary.isNullOrEmpty()) {
            Toast.makeText(requireContext(), "Please fill title and summary", Toast.LENGTH_SHORT).show()
            return
        }
        
        // Show loading message
        if (selectedImageUris.isNotEmpty()) {
            Toast.makeText(requireContext(), "Converting ${selectedImageUris.size} image(s) to Base64...", Toast.LENGTH_SHORT).show()
        }
        btnSaveStory.isEnabled = false

        viewLifecycleOwner.lifecycleScope.launch {
            try {
                // Convert selected images to Base64 strings
                val base64Images = uploadImagesToFirebase()
                
                android.util.Log.d("AdminStoriesEditor", "Successfully converted ${base64Images.size} images")
                
                val storyId = currentEditingStory?.id ?: UUID.randomUUID().toString()
                val adoptedUpdate = etAdoptedUpdate.text?.toString()?.trim()?.takeIf { it.isNotEmpty() }
                
                // Parse manual media URLs (comma-separated) from text field
                val mediaUrlsText = etMediaUrls.text?.toString()?.trim() ?: ""
                val manualMediaItems = if (mediaUrlsText.isNotEmpty()) {
                    mediaUrlsText.split(",").map { url ->
                        MediaItem(
                            type = if (url.contains("youtube") || url.contains("video")) MediaType.VIDEO else MediaType.IMAGE,
                            urlOrPath = url.trim()
                        )
                    }
                } else {
                    emptyList()
                }
                
                // Combine Base64 images with manual URLs
                val base64MediaItems = base64Images.map { base64 ->
                    MediaItem(
                        type = MediaType.IMAGE,
                        urlOrPath = base64  // Base64 string stored as urlOrPath
                    )
                }
                
                val allMediaItems = base64MediaItems + manualMediaItems

                val story = Story(
                    id = storyId,
                    title = title,
                    summary = summary,
                    fullContent = fullContent ?: summary,
                    adoptedUpdate = adoptedUpdate,
                    media = allMediaItems,
                    liked = false,
                    following = false,
                    timestamp = System.currentTimeMillis()
                )

                android.util.Log.d("AdminStoriesEditor", "Saving story with ${allMediaItems.size} media items")
                
                db.collection("stories").document(storyId)
                    .set(story)
                    .await()
                
                val successMessage = buildString {
                    append("Story saved successfully!")
                    if (base64Images.isNotEmpty()) {
                        append("\n${base64Images.size} image(s) saved")
                    }
                    if (manualMediaItems.isNotEmpty()) {
                        append("\n${manualMediaItems.size} manual URL(s) added")
                    }
                }
                Toast.makeText(requireContext(), successMessage, Toast.LENGTH_LONG).show()
                clearForm()
                loadStories()
                
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Failed to save story: ${e.message}", Toast.LENGTH_SHORT).show()
            } finally {
                btnSaveStory.isEnabled = true
            }
        }
    }

    private fun loadStoryToForm(story: Story) {
        currentEditingStory = story
        etStoryId.setText(story.id)
        etTitle.setText(story.title)
        etSummary.setText(story.summary)
        etFullContent.setText(story.fullContent)
        etAdoptedUpdate.setText(story.adoptedUpdate ?: "")
        
        val mediaUrls = story.media.joinToString(", ") { it.urlOrPath }
        etMediaUrls.setText(mediaUrls)
    }

    private fun clearForm() {
        currentEditingStory = null
        selectedImageUris.clear()
        selectedImagesContainer.removeAllViews()
        etStoryId.setText("")
        etTitle.setText("")
        etSummary.setText("")
        etFullContent.setText("")
        etAdoptedUpdate.setText("")
        etMediaUrls.setText("")
    }

    private fun deleteStory(story: Story) {
        db.collection("stories").document(story.id)
            .delete()
            .addOnSuccessListener {
                Toast.makeText(requireContext(), "Story deleted", Toast.LENGTH_SHORT).show()
                loadStories()
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), "Failed to delete story", Toast.LENGTH_SHORT).show()
            }
    }
}

class StoryEditorAdapter(
    private val stories: List<Story>,
    private val onAction: (Story, String) -> Unit
) : RecyclerView.Adapter<StoryEditorAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: android.view.ViewGroup, viewType: Int): ViewHolder {
        val view = android.view.LayoutInflater.from(parent.context)
            .inflate(R.layout.item_admin_story, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val story = stories[position]
        holder.bind(story)
    }

    override fun getItemCount() = stories.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tvTitle = view.findViewById<TextView>(R.id.tvStoryTitle)
        private val tvSummary = view.findViewById<TextView>(R.id.tvStorySummary)
        private val btnEdit = view.findViewById<Button>(R.id.btnEditStory)
        private val btnDelete = view.findViewById<Button>(R.id.btnDeleteStory)

        fun bind(story: Story) {
            tvTitle.text = story.title
            tvSummary.text = story.summary
            
            btnEdit.setOnClickListener {
                onAction(story, "edit")
            }
            
            btnDelete.setOnClickListener {
                onAction(story, "delete")
            }
        }
    }
}

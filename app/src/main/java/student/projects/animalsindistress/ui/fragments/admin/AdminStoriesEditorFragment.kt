package student.projects.animalsindistress.ui.fragments.admin

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import student.projects.animalsindistress.R
import student.projects.animalsindistress.data.models.Story
import student.projects.animalsindistress.data.models.MediaItem
import student.projects.animalsindistress.data.models.MediaType
import java.util.UUID

class AdminStoriesEditorFragment : Fragment(R.layout.fragment_admin_stories_editor) {

    private val db = Firebase.firestore
    private val stories = mutableListOf<Story>()
    private lateinit var adapter: StoryEditorAdapter
    private var currentEditingStory: Story? = null

    private lateinit var etStoryId: TextInputEditText
    private lateinit var etTitle: TextInputEditText
    private lateinit var etSummary: TextInputEditText
    private lateinit var etFullContent: TextInputEditText
    private lateinit var etAdoptedUpdate: TextInputEditText
    private lateinit var etMediaUrls: TextInputEditText
    private lateinit var btnSaveStory: Button

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
        
        // Setup clear button
        view.findViewById<Button>(R.id.btnClearForm)?.setOnClickListener {
            clearForm()
        }

        // Load existing stories
        loadStories()
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

        val storyId = currentEditingStory?.id ?: UUID.randomUUID().toString()
        val adoptedUpdate = etAdoptedUpdate.text?.toString()?.trim()?.takeIf { it.isNotEmpty() }
        
        // Parse media URLs (comma-separated)
        val mediaUrlsText = etMediaUrls.text?.toString()?.trim() ?: ""
        val mediaItems = if (mediaUrlsText.isNotEmpty()) {
            mediaUrlsText.split(",").map { url ->
                MediaItem(
                    type = if (url.contains("youtube") || url.contains("video")) MediaType.VIDEO else MediaType.IMAGE,
                    urlOrPath = url.trim()
                )
            }
        } else {
            emptyList()
        }

        val story = Story(
            id = storyId,
            title = title,
            summary = summary,
            fullContent = fullContent ?: summary,
            adoptedUpdate = adoptedUpdate,
            media = mediaItems,
            liked = false,
            following = false,
            timestamp = System.currentTimeMillis()
        )

        db.collection("stories").document(storyId)
            .set(story)
            .addOnSuccessListener {
                Toast.makeText(requireContext(), "Story saved successfully", Toast.LENGTH_SHORT).show()
                clearForm()
                loadStories()
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), "Failed to save story", Toast.LENGTH_SHORT).show()
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

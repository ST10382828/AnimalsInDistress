package student.projects.animalsindistress.ui.fragments

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import androidx.recyclerview.widget.RecyclerView
import coil.load
import student.projects.animalsindistress.R
import student.projects.animalsindistress.data.FirestoreStoryRepository
import student.projects.animalsindistress.data.models.MediaItem
import student.projects.animalsindistress.data.models.MediaType
import student.projects.animalsindistress.data.models.Story

class StoryDetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_story_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val heroImage: ImageView = view.findViewById(R.id.detailHeroImage)
        val titleText: TextView = view.findViewById(R.id.detailTitle)
        val fullContentText: TextView = view.findViewById(R.id.detailFullContent)
        val adoptedContainer: View = view.findViewById(R.id.adoptedContainer)
        val adoptedText: TextView = view.findViewById(R.id.adoptedUpdate)
        val mediaList: RecyclerView = view.findViewById(R.id.detailMediaList)

        val storyId = arguments?.getString("storyId")
        mediaList.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        if (storyId != null) {
            val repo = FirestoreStoryRepository(requireContext())
            viewLifecycleOwner.lifecycleScope.launch {
                val story = withContext(Dispatchers.IO) { repo.getStoryById(storyId) }
                story?.let {
                    titleText.text = it.title
                    fullContentText.text = it.fullContent
                    
                    // Load Hero Image
                    val firstMedia = it.media.firstOrNull()
                    val imagePath = when {
                        firstMedia == null -> null
                        firstMedia.type == MediaType.IMAGE -> firstMedia.urlOrPath
                        else -> firstMedia.thumbnailPath ?: firstMedia.urlOrPath
                    }
                    
                    if (imagePath != null) {
                        when {
                            imagePath.startsWith("data:image") -> {
                                try {
                                    val base64String = imagePath.substringAfter("base64,")
                                    val imageBytes = Base64.decode(base64String, Base64.DEFAULT)
                                    val bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
                                    heroImage.setImageBitmap(bitmap)
                                } catch (e: Exception) {
                                    heroImage.setImageResource(R.drawable.placeholder_animal)
                                }
                            }
                            imagePath.startsWith("http") -> {
                                heroImage.load(imagePath) {
                                    crossfade(300)
                                    placeholder(R.drawable.placeholder_animal)
                                    error(R.drawable.placeholder_animal)
                                }
                            }
                            else -> {
                                val resourceName = imagePath.substringBeforeLast('.')
                                val drawableId = requireContext().resources.getIdentifier(
                                    resourceName, "drawable", requireContext().packageName
                                )
                                if (drawableId != 0) heroImage.setImageResource(drawableId)
                                else heroImage.setImageResource(R.drawable.placeholder_animal)
                            }
                        }
                    } else {
                        heroImage.setImageResource(R.drawable.placeholder_animal)
                    }

                    if (!it.adoptedUpdate.isNullOrBlank()) {
                        adoptedText.text = "✨ Where are they now?\n${it.adoptedUpdate}"
                        adoptedContainer.visibility = View.VISIBLE
                    } else {
                        adoptedContainer.visibility = View.GONE
                    }
                    
                    mediaList.adapter = DetailMediaAdapter(it.media)
                }
            }
        }
    }
}

private class DetailMediaAdapter(private val items: List<MediaItem>) : RecyclerView.Adapter<DetailMediaVH>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailMediaVH {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_detail_media, parent, false)
        return DetailMediaVH(v)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: DetailMediaVH, position: Int) {
        holder.bind(items[position])
    }
}

private class DetailMediaVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val image: ImageView = itemView.findViewById(R.id.detailImage)
    private val label: TextView = itemView.findViewById(R.id.detailImageLabel)
    
    fun bind(item: MediaItem) {
        val path = if (item.type == MediaType.IMAGE) item.urlOrPath else item.thumbnailPath ?: item.urlOrPath
        
        // Load from URL (Firebase), Base64, or drawable resource
        when {
            path.startsWith("data:image") -> {
                // Decode Base64 image stored in Firestore
                try {
                    val base64String = path.substringAfter("base64,")
                    val imageBytes = Base64.decode(base64String, Base64.DEFAULT)
                    val bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
                    image.setImageBitmap(bitmap)
                } catch (e: Exception) {
                    image.setImageResource(R.drawable.placeholder_animal)
                }
            }
            path.startsWith("http://") || path.startsWith("https://") -> {
                // Load from URL (Firebase Storage or external)
                image.load(path) {
                    crossfade(300)
                    placeholder(R.drawable.placeholder_animal)
                    error(R.drawable.placeholder_animal)
                }
            }
            else -> {
                // Load from drawable resources by name (remove extension if present)
                val resourceName = path.substringBeforeLast('.') // Remove .jpg/.png extension
                val drawableId = itemView.context.resources.getIdentifier(
                    resourceName,
                    "drawable",
                    itemView.context.packageName
                )
                if (drawableId != 0) {
                    image.load(drawableId) {
                        crossfade(300)
                        placeholder(R.drawable.placeholder_animal)
                        error(R.drawable.placeholder_animal)
                    }
                } else {
                    image.setImageResource(R.drawable.placeholder_animal)
                }
            }
        }
        
        // Set label based on beforeAfterTag
        label.text = when (item.beforeAfterTag.toString()) {
            "BEFORE" -> "BEFORE"
            "AFTER" -> "AFTER"
            else -> if (item.type == MediaType.VIDEO) "VIDEO" else ""
        }
        label.visibility = if (label.text.isNotEmpty()) View.VISIBLE else View.GONE
    }
}



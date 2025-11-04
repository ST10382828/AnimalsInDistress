package student.projects.animalsindistress.ui.fragments

import android.os.Bundle
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
import student.projects.animalsindistress.data.LocalStoryRepository
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
        val titleText: TextView = view.findViewById(R.id.detailTitle)
        val fullContentText: TextView = view.findViewById(R.id.detailFullContent)
        val adoptedText: TextView = view.findViewById(R.id.adoptedUpdate)
        val mediaList: RecyclerView = view.findViewById(R.id.detailMediaList)

        val storyId = arguments?.getString("storyId")
        mediaList.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        if (storyId != null) {
            val repo = LocalStoryRepository(requireContext())
            viewLifecycleOwner.lifecycleScope.launch {
                val story = withContext(Dispatchers.IO) { repo.getStoryById(storyId) }
                story?.let {
                    titleText.text = it.title
                    fullContentText.text = it.fullContent
                    
                    if (!it.adoptedUpdate.isNullOrBlank()) {
                        adoptedText.text = "✨ Where are they now?\n${it.adoptedUpdate}"
                        adoptedText.visibility = View.VISIBLE
                    } else {
                        adoptedText.visibility = View.GONE
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
        
        // Set label based on beforeAfterTag
        label.text = when (item.beforeAfterTag.toString()) {
            "BEFORE" -> "BEFORE"
            "AFTER" -> "AFTER"
            else -> if (item.type == MediaType.VIDEO) "VIDEO" else ""
        }
        label.visibility = if (label.text.isNotEmpty()) View.VISIBLE else View.GONE
    }
}



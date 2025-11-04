package student.projects.animalsindistress.ui.fragments

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.content.Intent
import android.net.Uri
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import coil.load
import student.projects.animalsindistress.data.models.MediaType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import student.projects.animalsindistress.R
import student.projects.animalsindistress.data.FirestoreStoryRepository
import student.projects.animalsindistress.data.StoryRepository
import student.projects.animalsindistress.data.models.Story
import androidx.navigation.fragment.findNavController
import androidx.core.os.bundleOf
import com.google.android.material.snackbar.Snackbar
import student.projects.animalsindistress.util.DepthPageTransformer

class StoriesFeedFragment : Fragment() {

    private lateinit var viewPager: ViewPager2
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var loadingSpinner: View
    private lateinit var emptyState: View
    private lateinit var repository: StoryRepository
    private lateinit var adapter: StoriesPagerAdapter
    private var currentPage = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_stories_feed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        repository = FirestoreStoryRepository(requireContext())
        
        viewPager = view.findViewById(R.id.storiesViewPager)
        swipeRefresh = view.findViewById(R.id.swipeRefresh)
        loadingSpinner = view.findViewById(R.id.loadingSpinner)
        emptyState = view.findViewById(R.id.emptyState)
        
        setupSwipeRefresh()
        setupViewPager()
        
        loadPage(0)
    }
    
    private fun setupSwipeRefresh() {
        swipeRefresh.setColorSchemeResources(
            R.color.primary,
            R.color.secondary,
            android.R.color.holo_green_light
        )
        
        swipeRefresh.setOnRefreshListener {
            refreshStories()
        }
        
        // Disable swipe refresh when not at the first story
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                swipeRefresh.isEnabled = position == 0
            }
        })
    }
    
    private fun setupViewPager() {
        viewPager.orientation = ViewPager2.ORIENTATION_VERTICAL
        // Add page transformer for smooth depth effect
        viewPager.setPageTransformer(DepthPageTransformer())
        // Reduce overdraw when pages are off-screen
        (viewPager.getChildAt(0) as? RecyclerView)?.overScrollMode = View.OVER_SCROLL_NEVER

        adapter = StoriesPagerAdapter(
            onItemVisible = { /* hook for preloading */ },
            onLikeClick = { story ->
                lifecycleScope.launch {
                    val updated = withContext(Dispatchers.IO) { repository.toggleLike(story.id) }
                    updated?.let { adapter.updateItem(it) }
                }
            },
            onShareClick = { story ->
                val shareText = "${story.title} — ${story.summary}\nLearn more in the Animals In Distress app."
                val intent = Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_TEXT, shareText)
                }
                startActivity(Intent.createChooser(intent, getString(R.string.app_name)))
            },
            onOpenDetail = { story ->
                findNavController().navigate(
                    R.id.action_storiesFeed_to_storyDetail,
                    bundleOf("storyId" to story.id)
                )
            }
        )
        viewPager.adapter = adapter
    }
    
    private fun refreshStories() {
        lifecycleScope.launch {
            (repository as? FirestoreStoryRepository)?.clearCache()
            val items: List<Story> = withContext(Dispatchers.IO) {
                repository.getStories(page = 0, pageSize = 10)
            }
            swipeRefresh.isRefreshing = false
            
            if (items.isNotEmpty()) {
                adapter.submit(items)
                viewPager.setCurrentItem(0, false)
                Snackbar.make(requireView(), "Stories refreshed!", Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    private fun loadPage(page: Int) {
        lifecycleScope.launch {
            showLoading(true)
            val items: List<Story> = withContext(Dispatchers.IO) {
                repository.getStories(page = page, pageSize = 10)
            }
            showLoading(false)
            
            if (items.isEmpty()) {
                showEmptyState(true)
            } else {
                showEmptyState(false)
                adapter.submit(items)
            }
        }
    }
    
    private fun showLoading(show: Boolean) {
        loadingSpinner.visibility = if (show) View.VISIBLE else View.GONE
        viewPager.visibility = if (show) View.GONE else View.VISIBLE
        swipeRefresh.isEnabled = !show
    }
    
    private fun showEmptyState(show: Boolean) {
        emptyState.visibility = if (show) View.VISIBLE else View.GONE
        viewPager.visibility = if (show) View.GONE else View.VISIBLE
    }
}

private class StoriesPagerAdapter(
    private val onItemVisible: (Int) -> Unit,
    private val onLikeClick: (Story) -> Unit,
    private val onShareClick: (Story) -> Unit,
    private val onOpenDetail: (Story) -> Unit
) : RecyclerView.Adapter<StoryItemViewHolder>() {

    private val data = mutableListOf<Story>()

    fun submit(items: List<Story>) {
        data.clear()
        data.addAll(items)
        notifyDataSetChanged()
    }

    fun updateItem(updated: Story) {
        val index = data.indexOfFirst { it.id == updated.id }
        if (index >= 0) {
            data[index] = updated
            notifyItemChanged(index)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryItemViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_story_page, parent, false)
        return StoryItemViewHolder(itemView, onLikeClick, onShareClick, onOpenDetail)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: StoryItemViewHolder, position: Int) {
        holder.bind(data[position])
        onItemVisible(position)
    }
}

private class StoryItemViewHolder(
    itemView: View,
    private val onLikeClick: (Story) -> Unit,
    private val onShareClick: (Story) -> Unit,
    private val onOpenDetail: (Story) -> Unit
) : RecyclerView.ViewHolder(itemView) {
    private val mediaImage: ImageView = itemView.findViewById(R.id.mediaImage)
    private val videoBadge: ImageView = itemView.findViewById(R.id.videoBadge)
    private val titleText: TextView = itemView.findViewById(R.id.titleText)
    private val summaryText: TextView = itemView.findViewById(R.id.summaryText)
    private val likeButton: ImageButton = itemView.findViewById(R.id.likeButton)
    private val shareButton: ImageButton = itemView.findViewById(R.id.shareButton)
    private val whereNowChip: TextView = itemView.findViewById(R.id.whereNowChip)

    private var bound: Story? = null

    fun bind(story: Story) {
        bound = story
        titleText.text = story.title
        summaryText.text = story.summary
        whereNowChip.visibility = if (story.adoptedUpdate.isNullOrBlank()) View.GONE else View.VISIBLE
        whereNowChip.setOnClickListener { onOpenDetail(story) }

        val firstMedia = story.media.firstOrNull()
        val imagePath = when {
            firstMedia == null -> null
            firstMedia.type == MediaType.IMAGE -> firstMedia.urlOrPath
            else -> firstMedia.thumbnailPath ?: firstMedia.urlOrPath
        }
        
        // Load from URL (Firebase), Base64, or drawable resource
        if (imagePath != null) {
            when {
                imagePath.startsWith("data:image") -> {
                    // Decode Base64 image stored in Firestore
                    try {
                        val base64String = imagePath.substringAfter("base64,")
                        val imageBytes = Base64.decode(base64String, Base64.DEFAULT)
                        val bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
                        mediaImage.setImageBitmap(bitmap)
                    } catch (e: Exception) {
                        mediaImage.setImageResource(R.drawable.placeholder_animal)
                    }
                }
                imagePath.startsWith("http://") || imagePath.startsWith("https://") -> {
                    // Load from URL (Firebase Storage or external)
                    mediaImage.load(imagePath) {
                        crossfade(300)
                        placeholder(R.drawable.placeholder_animal)
                        error(R.drawable.placeholder_animal)
                    }
                }
                else -> {
                    // Load from drawable resources by name (remove extension if present)
                    val resourceName = imagePath.substringBeforeLast('.') // Remove .jpg/.png extension
                    val drawableId = itemView.context.resources.getIdentifier(
                        resourceName,
                        "drawable",
                        itemView.context.packageName
                    )
                    if (drawableId != 0) {
                        mediaImage.load(drawableId) {
                            crossfade(300)
                            placeholder(R.drawable.placeholder_animal)
                            error(R.drawable.placeholder_animal)
                        }
                    } else {
                        mediaImage.setImageResource(R.drawable.placeholder_animal)
                    }
                }
            }
        } else {
            mediaImage.setImageResource(R.drawable.placeholder_animal)
        }
        videoBadge.visibility = if (firstMedia?.type == MediaType.VIDEO) View.VISIBLE else View.GONE

        // Animate like button
        val likedIcon = if (story.liked) android.R.drawable.btn_star_big_on else android.R.drawable.btn_star_big_off
        likeButton.setImageResource(likedIcon)
        // Set tint color based on liked state
        val likeColor = if (story.liked) 0xFFFFD700.toInt() else 0xFFFFFFFF.toInt()  // Gold when liked, white when not
        likeButton.setColorFilter(likeColor, android.graphics.PorterDuff.Mode.SRC_IN)
        
        likeButton.setOnClickListener { 
            // Add quick scale animation
            it.animate()
                .scaleX(1.3f)
                .scaleY(1.3f)
                .setDuration(150)
                .withEndAction {
                    it.animate().scaleX(1f).scaleY(1f).setDuration(150).start()
                }
                .start()
            onLikeClick(story)
        }
        shareButton.setOnClickListener { onShareClick(story) }
        itemView.setOnClickListener { onOpenDetail(story) }
    }
}



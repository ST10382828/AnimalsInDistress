package student.projects.animalsindistress.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import coil.load
import coil.dispose
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import student.projects.animalsindistress.R
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import student.projects.animalsindistress.data.GalleryImage
import android.graphics.BitmapFactory
import android.util.Base64
import android.app.Dialog
import android.widget.ImageButton
import android.widget.TextView
import android.util.Log
import com.google.android.material.card.MaterialCardView

class GalleryFragment : Fragment(R.layout.fragment_gallery) {
    private val db = Firebase.firestore
    private var firestoreImages = mutableListOf<GalleryImage>()
    private var allImageData = mutableListOf<ImageData>() // Store image data with metadata
    private var currentCategory = "all" // Track current category
    private lateinit var adapter: GalleryAdapter
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    
    // Data class to hold image information
    data class ImageData(
        val url: String,
        val title: String? = null,
        val description: String? = null,
        val category: String? = null
    )
    private val memoryImages = listOf(
        "https://animalsindistress.org.za/wp-content/uploads/2023/06/SAID-Memory-Gallery1.png",
        "https://animalsindistress.org.za/wp-content/uploads/2023/06/SAID-Memory-Gallery2.png",
        "https://animalsindistress.org.za/wp-content/uploads/2023/06/SAID-Memory-Gallery3.png",
        "https://animalsindistress.org.za/wp-content/uploads/2023/06/SAID-Memory-Gallery4.png",
        "https://animalsindistress.org.za/wp-content/uploads/2023/06/SAID-Memory-Gallery5.png",
        "https://animalsindistress.org.za/wp-content/uploads/2023/06/SAID-Memory-Gallery6.png",
        "https://animalsindistress.org.za/wp-content/uploads/2023/06/SAID-Memory-Gallery7.png",
        "https://animalsindistress.org.za/wp-content/uploads/2023/06/SAID-Memory-Gallery8.png",
        "https://animalsindistress.org.za/wp-content/uploads/2023/06/SAID-Memory-Gallery9.png",
        "https://animalsindistress.org.za/wp-content/uploads/2023/06/SAID-Memory-Gallery10.png"
    )

    private val animalsImages = listOf(
        "https://animalsindistress.org.za/wp-content/uploads/2023/06/SAID-Animals-Gallery1.png",
        "https://animalsindistress.org.za/wp-content/uploads/2023/06/SAID-Animals-Gallery2.png",
        "https://animalsindistress.org.za/wp-content/uploads/2023/06/SAID-Animals-Gallery3.png",
        "https://animalsindistress.org.za/wp-content/uploads/2023/06/SAID-Animals-Gallery4.png",
        "https://animalsindistress.org.za/wp-content/uploads/2023/06/SAID-Animals-Gallery5.png",
        "https://animalsindistress.org.za/wp-content/uploads/2023/06/SAID-Animals-Gallery6.png",
        "https://animalsindistress.org.za/wp-content/uploads/2023/06/SAID-Animals-Gallery7.png",
        "https://animalsindistress.org.za/wp-content/uploads/2023/06/SAID-Animals-Gallery8.png",
        "https://animalsindistress.org.za/wp-content/uploads/2023/06/SAID-Animals-Gallery9.png",
        "https://animalsindistress.org.za/wp-content/uploads/2023/06/SAID-Animals-Gallery10.png"
    )

    private val volunteersImages = listOf(
        "https://animalsindistress.org.za/wp-content/uploads/2023/06/SAID-Volunteers-Gallery1.png",
        "https://animalsindistress.org.za/wp-content/uploads/2023/06/SAID-Volunteers-Gallery2-1.png",
        "https://animalsindistress.org.za/wp-content/uploads/2023/06/SAID-Volunteers-Gallery3.png",
        "https://animalsindistress.org.za/wp-content/uploads/2023/06/SAID-Volunteers-Gallery4.png",
        "https://animalsindistress.org.za/wp-content/uploads/2023/06/SAID-Volunteers-Gallery5.png"
    )

    private val diamondsImages = listOf(
        "https://animalsindistress.org.za/wp-content/uploads/2023/06/SAID-Diamonds-Gallery1.png",
        "https://animalsindistress.org.za/wp-content/uploads/2023/06/SAID-Diamonds-Gallery2.png",
        "https://animalsindistress.org.za/wp-content/uploads/2023/06/SAID-Diamonds-Gallery3.png",
        "https://animalsindistress.org.za/wp-content/uploads/2023/06/SAID-Diamonds-Gallery4.png",
        "https://animalsindistress.org.za/wp-content/uploads/2023/06/SAID-Diamonds-Gallery5.png",
        "https://animalsindistress.org.za/wp-content/uploads/2023/06/SAID-Diamonds-Gallery6.png",
        "https://animalsindistress.org.za/wp-content/uploads/2023/06/SAID-Diamonds-Gallery7.png",
        "https://animalsindistress.org.za/wp-content/uploads/2023/06/SAID-Diamonds-Gallery8.png"
    )

    private val horsesImages = listOf(
        "https://animalsindistress.org.za/wp-content/uploads/2024/06/AVALANCHE-SAID.jpg",
        "https://animalsindistress.org.za/wp-content/uploads/2024/06/AVALANCHE-SAID-3.jpg",
        "https://animalsindistress.org.za/wp-content/uploads/2024/06/AVALANCHE-SAID-2.jpg",
        "https://animalsindistress.org.za/wp-content/uploads/2024/06/VET-TREATMENT-HORSES.jpg",
        "https://animalsindistress.org.za/wp-content/uploads/2024/06/SAID-HORSES-4.jpg",
        "https://animalsindistress.org.za/wp-content/uploads/2024/06/MEG-AND-AVALANCHE-SAID-scaled.jpg",
        "https://animalsindistress.org.za/wp-content/uploads/2024/06/SAID-HORSES-3.jpg",
        "https://animalsindistress.org.za/wp-content/uploads/2024/06/SAID-HORSES-2.jpg"
    )

    private val eventsImages = listOf(
        "https://animalsindistress.org.za/wp-content/uploads/2023/06/IMG_20180411_111118-1.jpg",
        "https://animalsindistress.org.za/wp-content/uploads/2023/06/IMG_20180914_084855-1.jpg",
        "https://animalsindistress.org.za/wp-content/uploads/2023/06/IMG_20180914_090652.jpg",
        "https://animalsindistress.org.za/wp-content/uploads/2023/06/IMG_20180914_091659-1.jpg",
        "https://animalsindistress.org.za/wp-content/uploads/2023/06/IMG_0461-1.jpg",
        "https://animalsindistress.org.za/wp-content/uploads/2023/06/IMG_1828-1.jpg",
        "https://animalsindistress.org.za/wp-content/uploads/2023/06/IMG_3813-1.jpg",
        "https://animalsindistress.org.za/wp-content/uploads/2023/06/IMG_5325.jpg"
    )

    private val categoryKeyToImages: Map<Int, List<String>> by lazy {
        mapOf(
            R.id.chip_all to (memoryImages + animalsImages + volunteersImages + diamondsImages + horsesImages + eventsImages),
            R.id.chip_memory to memoryImages,
            R.id.chip_animals to animalsImages,
            R.id.chip_volunteers to volunteersImages,
            R.id.chip_diamonds to diamondsImages,
            R.id.chip_horses to horsesImages,
            R.id.chip_events to eventsImages,
        )
    }

    private fun getMergedImages(category: String): List<ImageData> {
        val hardcodedUrls = when (category) {
            "memory" -> memoryImages
            "animals" -> animalsImages
            "volunteers" -> volunteersImages
            "diamonds" -> diamondsImages
            "horses" -> horsesImages
            "events" -> eventsImages
            "all" -> (memoryImages + animalsImages + volunteersImages + diamondsImages + horsesImages + eventsImages)
            else -> emptyList()
        }.map { ImageData(it, category = category) }

        val firestoreData = firestoreImages
            .filter { it.category == category || category == "all" || it.category == "all" }
            .map { ImageData(it.imageUrl, it.title, it.description, it.category) }

        return firestoreData + hardcodedUrls
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.galleryRecyclerView)
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout)
        
        // Optimize RecyclerView performance
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        recyclerView.setHasFixedSize(true) // All items same size
        recyclerView.setItemViewCacheSize(20) // Cache more items for smoother scrolling
        
        adapter = GalleryAdapter { imageData ->
            // Show full-view dialog when image is clicked
            showFullImageDialog(imageData)
        }
        recyclerView.adapter = adapter

        // Configure swipe refresh colors
        swipeRefreshLayout.setColorSchemeResources(
            R.color.primary,
            R.color.secondary
        )
        
        // Handle swipe-to-refresh
        swipeRefreshLayout.setOnRefreshListener {
            Log.d("GalleryFragment", "Manual refresh triggered")
            refreshGallery()
        }

        val chipGroup = view.findViewById<ChipGroup>(R.id.chip_group)
        
        // Set colors for all chips
        val primaryColor = resources.getColor(R.color.primary, null)
        val whiteColor = resources.getColor(R.color.white, null)
        val mutedColor = resources.getColor(R.color.muted_foreground, null)
        
        listOf(R.id.chip_all, R.id.chip_memory, R.id.chip_animals, R.id.chip_volunteers, 
               R.id.chip_diamonds, R.id.chip_horses, R.id.chip_events).forEach { chipId ->
            view.findViewById<Chip>(chipId)?.apply {
                chipBackgroundColor = android.content.res.ColorStateList(
                    arrayOf(
                        intArrayOf(android.R.attr.state_checked),
                        intArrayOf()
                    ),
                    intArrayOf(primaryColor, whiteColor)
                )
                setTextColor(android.content.res.ColorStateList(
                    arrayOf(
                        intArrayOf(android.R.attr.state_checked),
                        intArrayOf()
                    ),
                    intArrayOf(whiteColor, mutedColor)
                ))
            }
        }
        
        // Load Firestore images
        loadFirestoreImages()
        
        // Set initial category after a short delay to ensure Firestore loaded
        view.postDelayed({
            view.findViewById<Chip>(R.id.chip_all)?.isChecked = true
            updateGallery("all")
        }, 300)

        chipGroup.setOnCheckedStateChangeListener { _, checkedIds ->
            val id = checkedIds.firstOrNull() ?: R.id.chip_all
            val category = when (id) {
                R.id.chip_memory -> "memory"
                R.id.chip_animals -> "animals"
                R.id.chip_volunteers -> "volunteers"
                R.id.chip_diamonds -> "diamonds"
                R.id.chip_horses -> "horses"
                R.id.chip_events -> "events"
                else -> "all"
            }
            currentCategory = category
            updateGallery(category)
        }
    }
    
    private fun refreshGallery() {
        Log.d("GalleryFragment", "Manual refresh triggered")
        // Firestore SnapshotListener will auto-update firestoreImages
        // Just wait briefly then update UI with fresh data
        view?.postDelayed({
            updateGallery(currentCategory)
            swipeRefreshLayout.isRefreshing = false
            Log.d("GalleryFragment", "Gallery refreshed")
        }, 500)
    }
    
    private fun updateGallery(category: String) {
        // Clear adapter first to prevent showing stale images
        adapter.clear()
        // Then submit new images
        adapter.submit(getMergedImages(category))
    }

    private fun loadFirestoreImages() {
        db.collection("gallery_images")
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    Log.e("GalleryFragment", "Error loading Firestore images", error)
                    return@addSnapshotListener
                }

                firestoreImages.clear()
                snapshot?.documents?.forEach { doc ->
                    val image = doc.toObject(GalleryImage::class.java)?.copy(id = doc.id)
                    image?.let { firestoreImages.add(it) }
                }
                Log.d("GalleryFragment", "Loaded ${firestoreImages.size} Firestore images")
                
                // SnapshotListener only updates the data, NOT the UI
                // UI updates happen via refreshGallery() or filter chips only
            }
    }

    private fun showFullImageDialog(imageData: ImageData) {
        val dialog = Dialog(requireContext(), android.R.style.Theme_Black_NoTitleBar_Fullscreen)
        dialog.setContentView(R.layout.dialog_image_fullview)

        val fullImageView = dialog.findViewById<ImageView>(R.id.fullImageView)
        val closeButton = dialog.findViewById<ImageButton>(R.id.closeButton)
        val imageInfoCard = dialog.findViewById<MaterialCardView>(R.id.imageInfoCard)
        val imageTitle = dialog.findViewById<TextView>(R.id.imageTitle)
        val imageDescription = dialog.findViewById<TextView>(R.id.imageDescription)
        val imageCategoryChip = dialog.findViewById<Chip>(R.id.imageCategoryChip)

        // Load the image (handle Base64 or URL)
        if (imageData.url.startsWith("data:image")) {
            val base64String = imageData.url.substringAfter("base64,")
            val imageBytes = Base64.decode(base64String, Base64.DEFAULT)
            val bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
            fullImageView.setImageBitmap(bitmap)
        } else {
            fullImageView.load(imageData.url) {
                crossfade(400) // Smooth transition
                placeholder(android.R.color.black) // Black background while loading
                // NO size limit - load full resolution for full-view
                memoryCacheKey(imageData.url + "_full") // Separate cache for full-res
            }
        }

        // Show/hide info card based on whether we have metadata
        val hasInfo = !imageData.title.isNullOrBlank() || 
                     !imageData.description.isNullOrBlank() || 
                     !imageData.category.isNullOrBlank()
        
        if (hasInfo) {
            imageInfoCard.visibility = View.VISIBLE
            
            if (!imageData.title.isNullOrBlank()) {
                imageTitle.visibility = View.VISIBLE
                imageTitle.text = imageData.title
            } else {
                imageTitle.visibility = View.GONE
            }
            
            if (!imageData.description.isNullOrBlank()) {
                imageDescription.visibility = View.VISIBLE
                imageDescription.text = imageData.description
            } else {
                imageDescription.visibility = View.GONE
            }
            
            if (!imageData.category.isNullOrBlank()) {
                imageCategoryChip.visibility = View.VISIBLE
                imageCategoryChip.text = imageData.category.replaceFirstChar { it.uppercase() }
            } else {
                imageCategoryChip.visibility = View.GONE
            }
        } else {
            imageInfoCard.visibility = View.GONE
        }

        closeButton.setOnClickListener {
            dialog.dismiss()
        }

        // Allow clicking anywhere to dismiss
        fullImageView.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    private class GalleryAdapter(
        private val onImageClick: (ImageData) -> Unit
    ) : RecyclerView.Adapter<GalleryViewHolder>() {
        private val items: MutableList<ImageData> = mutableListOf()

        fun clear() {
            val oldSize = items.size
            items.clear()
            notifyItemRangeRemoved(0, oldSize)
            Log.d("GalleryAdapter", "Cleared all images")
        }

        fun submit(imageDataList: List<ImageData>) {
            items.clear()
            items.addAll(imageDataList)
            Log.d("GalleryAdapter", "Submitted ${items.size} images")
            notifyDataSetChanged()
        }

        override fun onCreateViewHolder(parent: android.view.ViewGroup, viewType: Int): GalleryViewHolder {
            val v = android.view.LayoutInflater.from(parent.context)
                .inflate(R.layout.item_gallery_image, parent, false)
            return GalleryViewHolder(v, onImageClick)
        }

        override fun getItemCount(): Int = items.size

        override fun onBindViewHolder(holder: GalleryViewHolder, position: Int) {
            holder.bind(items[position])
        }
    }

    private class GalleryViewHolder(
        itemView: View,
        private val onImageClick: (ImageData) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {
        private val image: ImageView = itemView.findViewById(R.id.image)
        
        fun bind(imageData: ImageData) {
            // CRITICAL: Cancel any pending Coil request to prevent showing stale images
            image.dispose()
            
            // Handle Base64 or URL with optimized loading
            if (imageData.url.startsWith("data:image")) {
                val base64String = imageData.url.substringAfter("base64,")
                val imageBytes = Base64.decode(base64String, Base64.DEFAULT)
                val bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
                image.setImageBitmap(bitmap)
            } else {
                image.load(imageData.url) {
                    crossfade(false) // Disable crossfade to prevent flashing old images
                    placeholder(android.R.color.darker_gray) // Show gray while loading
                    memoryCacheKey(imageData.url) // Explicit cache key
                    diskCacheKey(imageData.url)
                    size(400, 400) // Thumbnail size for grid - much faster
                }
            }
            
            // Set click listener
            itemView.setOnClickListener {
                onImageClick(imageData)
            }
        }
    }
}



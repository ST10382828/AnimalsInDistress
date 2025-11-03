package student.projects.animalsindistress.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import student.projects.animalsindistress.R

class GalleryFragment : Fragment(R.layout.fragment_gallery) {
    
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.galleryRecyclerView)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        val adapter = GalleryAdapter()
        recyclerView.adapter = adapter

        val chipGroup = view.findViewById<ChipGroup>(R.id.chip_group)
        // default select "All"
        view.findViewById<Chip>(R.id.chip_all)?.isChecked = true
        adapter.submit(categoryKeyToImages[R.id.chip_all] ?: emptyList())

        chipGroup.setOnCheckedStateChangeListener { _, checkedIds ->
            val id = checkedIds.firstOrNull() ?: R.id.chip_all
            adapter.submit(categoryKeyToImages[id] ?: emptyList())
        }
    }

    private class GalleryAdapter : RecyclerView.Adapter<GalleryViewHolder>() {
        private val items: MutableList<String> = mutableListOf()

        fun submit(urls: List<String>) {
            items.clear()
            items.addAll(urls)
            notifyDataSetChanged()
        }

        override fun onCreateViewHolder(parent: android.view.ViewGroup, viewType: Int): GalleryViewHolder {
            val v = android.view.LayoutInflater.from(parent.context)
                .inflate(R.layout.item_gallery_image, parent, false)
            return GalleryViewHolder(v)
        }

        override fun getItemCount(): Int = items.size

        override fun onBindViewHolder(holder: GalleryViewHolder, position: Int) {
            holder.bind(items[position])
        }
    }

    private class GalleryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val image: ImageView = itemView.findViewById(R.id.image)
        fun bind(url: String) {
            image.load(url)
        }
    }
}

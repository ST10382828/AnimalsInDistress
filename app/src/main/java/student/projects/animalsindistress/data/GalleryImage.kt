package student.projects.animalsindistress.data

import com.google.firebase.Timestamp

data class GalleryImage(
    val id: String = "",
    val imageUrl: String = "", // Can be URL or Base64
    val category: String = "all", // all, memory, animals, volunteers, diamonds, horses, events
    val title: String = "",
    val description: String = "",
    val timestamp: Timestamp = Timestamp.now(),
    val addedBy: String = "" // Admin user ID
)

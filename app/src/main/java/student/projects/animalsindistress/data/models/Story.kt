package student.projects.animalsindistress.data.models

data class Story(
    val id: String,
    val title: String,
    val summary: String,
    val fullContent: String = summary, // Full story for detail view
    val adoptedUpdate: String? = null,
    val media: List<MediaItem> = emptyList(),
    val liked: Boolean = false,
    val following: Boolean = false,
    val timestamp: Long = System.currentTimeMillis() // For sorting
)




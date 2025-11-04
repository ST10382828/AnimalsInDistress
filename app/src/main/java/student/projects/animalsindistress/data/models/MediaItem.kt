package student.projects.animalsindistress.data.models

enum class MediaType {
    IMAGE,
    VIDEO
}

enum class BeforeAfterTag {
    BEFORE,
    AFTER,
    NONE
}

data class MediaItem(
    val type: MediaType,
    val urlOrPath: String,
    val thumbnailPath: String? = null,
    val beforeAfterTag: BeforeAfterTag = BeforeAfterTag.NONE
)



package student.projects.animalsindistress.data

import com.google.firebase.Timestamp

data class MedicalAidRequest(
    val id: String = "",
    val userId: String = "",
    val userName: String = "",
    val userEmail: String = "",
    val phone: String = "",
    val location: String = "",
    val latitude: Double? = null,
    val longitude: Double? = null,
    val description: String = "",
    val imageUrl: String? = null,
    val timestamp: Timestamp = Timestamp.now(),
    val status: String = "pending", // pending, in_progress, completed
    val adminNotes: String = ""
)


package student.projects.animalsindistress.data

import com.google.firebase.Timestamp

data class ContactSubmission(
    val id: String = "",
    val name: String = "",
    val email: String = "",
    val phone: String = "",
    val message: String = "",
    val timestamp: Timestamp = Timestamp.now(),
    val read: Boolean = false
)

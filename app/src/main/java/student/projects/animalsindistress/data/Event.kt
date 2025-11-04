package student.projects.animalsindistress.data

import java.time.LocalDate
import java.time.LocalTime

data class Event(
    val id: String,
    val title: String,
    val description: String,
    val date: LocalDate,
    val startTime: LocalTime,
    val endTime: LocalTime,
    val location: String,
    val category: EventCategory,
    val rsvpRequired: Boolean = false,
    val rsvpCount: Int = 0,
    val maxAttendees: Int? = null
)

enum class EventCategory(val displayName: String, val color: Long) {
    GOLF_DAY("Golf Day", 0xFF10B981), // Green
    FUNDRAISER("Fundraiser", 0xFF0052CC), // Blue
    MOBILE_CLINIC("Mobile Clinic", 0xFFF59E0B), // Orange
    GENERAL("General Event", 0xFF6B7280) // Gray
}


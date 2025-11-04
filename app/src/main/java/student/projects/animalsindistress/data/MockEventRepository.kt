package student.projects.animalsindistress.data

import java.time.LocalDate
import java.time.LocalTime

object MockEventRepository {
    fun getEvents(): List<Event> {
        return listOf(
            Event(
                id = "1",
                title = "Annual Golf Day Tournament",
                description = "Join us for our annual golf day tournament supporting Animals in Distress. All skill levels welcome!",
                date = LocalDate.now().plusDays(14),
                startTime = LocalTime.of(8, 0),
                endTime = LocalTime.of(16, 0),
                location = "Pebble Beach Golf Club",
                category = EventCategory.GOLF_DAY,
                rsvpRequired = true,
                rsvpCount = 45,
                maxAttendees = 100
            ),
            Event(
                id = "2",
                title = "Spring Fundraiser Gala",
                description = "An elegant evening of dining and entertainment to raise funds for our animal rescue operations.",
                date = LocalDate.now().plusDays(7),
                startTime = LocalTime.of(18, 30),
                endTime = LocalTime.of(23, 0),
                location = "Grand Ballroom, Downtown Hotel",
                category = EventCategory.FUNDRAISER,
                rsvpRequired = true,
                rsvpCount = 120,
                maxAttendees = 150
            ),
            Event(
                id = "3",
                title = "Mobile Clinic - Northside Community",
                description = "Free vaccination and microchipping services for pets in the Northside area.",
                date = LocalDate.now().plusDays(3),
                startTime = LocalTime.of(10, 0),
                endTime = LocalTime.of(15, 0),
                location = "Northside Community Center",
                category = EventCategory.MOBILE_CLINIC,
                rsvpRequired = false,
                rsvpCount = 35,
                maxAttendees = null
            ),
            Event(
                id = "4",
                title = "Mobile Clinic - Southside Community",
                description = "Free vaccination and microchipping services for pets in the Southside area.",
                date = LocalDate.now().plusDays(10),
                startTime = LocalTime.of(10, 0),
                endTime = LocalTime.of(15, 0),
                location = "Southside Park Pavilion",
                category = EventCategory.MOBILE_CLINIC,
                rsvpRequired = false,
                rsvpCount = 18,
                maxAttendees = null
            ),
            Event(
                id = "5",
                title = "Monthly Volunteer Meeting",
                description = "Monthly meeting for volunteers to coordinate upcoming events and share updates.",
                date = LocalDate.now().plusDays(21),
                startTime = LocalTime.of(19, 0),
                endTime = LocalTime.of(20, 30),
                location = "Main Office - Conference Room",
                category = EventCategory.GENERAL,
                rsvpRequired = true,
                rsvpCount = 15,
                maxAttendees = 30
            ),
            Event(
                id = "6",
                title = "Adoption Day Event",
                description = "Meet our adorable rescue animals and find your new best friend!",
                date = LocalDate.now().plusDays(5),
                startTime = LocalTime.of(12, 0),
                endTime = LocalTime.of(17, 0),
                location = "Community Adoption Center",
                category = EventCategory.GENERAL,
                rsvpRequired = false,
                rsvpCount = 52,
                maxAttendees = null
            )
        )
    }
}


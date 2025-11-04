package student.projects.animalsindistress.ui.fragments.event

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import student.projects.animalsindistress.R
import student.projects.animalsindistress.data.Event
import java.time.format.DateTimeFormatter

class EventAdapter(
    private val events: List<Event>,
    private val onEventClick: (Event) -> Unit
) : RecyclerView.Adapter<EventAdapter.EventViewHolder>() {

    class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardView: MaterialCardView = itemView.findViewById(R.id.itemEventCard)
        val categoryIndicator: View = itemView.findViewById(R.id.viewCategoryIndicator)
        val tvCategory: TextView = itemView.findViewById(R.id.tvCategory)
        val tvEventTitle: TextView = itemView.findViewById(R.id.tvEventTitle)
        val tvDate: TextView = itemView.findViewById(R.id.tvDate)
        val tvTime: TextView = itemView.findViewById(R.id.tvTime)
        val tvLocation: TextView = itemView.findViewById(R.id.tvLocation)
        val cardRSVP: MaterialCardView = itemView.findViewById(R.id.cardRSVP)
        val tvRSVPCount: TextView = itemView.findViewById(R.id.tvRSVPCount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_event_card, parent, false)
        return EventViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = events[position]

        // Category color
        holder.categoryIndicator.setBackgroundColor(event.category.color.toInt())
        holder.tvCategory.text = event.category.displayName
        holder.tvCategory.setTextColor(event.category.color.toInt())

        // Title
        holder.tvEventTitle.text = event.title

        // Date and Time
        val dateFormatter = DateTimeFormatter.ofPattern("d MMM")
        holder.tvDate.text = event.date.format(dateFormatter)
        val timeFormatter = DateTimeFormatter.ofPattern("h:mm a")
        holder.tvTime.text = "${event.startTime.format(timeFormatter)} - ${event.endTime.format(timeFormatter)}"

        // Location
        holder.tvLocation.text = event.location

        // RSVP
        if (event.rsvpRequired) {
            holder.cardRSVP.visibility = View.VISIBLE
            val rsvpText = if (event.maxAttendees != null) {
                "${event.rsvpCount} / ${event.maxAttendees} attending"
            } else {
                "${event.rsvpCount} attending"
            }
            holder.tvRSVPCount.text = rsvpText
        } else {
            holder.cardRSVP.visibility = View.GONE
        }

        // Click listener
        holder.cardView.setOnClickListener {
            onEventClick(event)
        }
    }

    override fun getItemCount(): Int = events.size
}


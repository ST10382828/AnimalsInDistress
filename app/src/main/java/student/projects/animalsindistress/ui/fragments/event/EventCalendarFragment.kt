package student.projects.animalsindistress.ui.fragments.event

import student.projects.animalsindistress.R
import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.provider.CalendarContract
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.app.NotificationCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import student.projects.animalsindistress.MainActivity
import student.projects.animalsindistress.NotificationReceiver
import student.projects.animalsindistress.data.Event
import student.projects.animalsindistress.data.EventCategory
import student.projects.animalsindistress.data.MockEventRepository
import student.projects.animalsindistress.ui.fragments.event.EventAdapter
import java.time.*
import java.time.format.DateTimeFormatter
import java.util.*

class EventCalendarViewModel : ViewModel() {
    private val _events = MutableLiveData<List<Event>>(MockEventRepository.getEvents())
    val events: LiveData<List<Event>> = _events

    fun addEvent(event: Event) {
        val currentEvents = _events.value?.toMutableList() ?: mutableListOf()
        currentEvents.add(event)
        _events.value = currentEvents
    }

    fun updateEvent(event: Event) {
        val currentEvents = _events.value?.toMutableList() ?: mutableListOf()
        val index = currentEvents.indexOfFirst { it.id == event.id }
        if (index != -1) {
            currentEvents[index] = event
            _events.value = currentEvents
        }
    }

    fun deleteEvent(event: Event) {
        val currentEvents = _events.value?.toMutableList() ?: mutableListOf()
        currentEvents.removeAll { it.id == event.id }
        _events.value = currentEvents
    }
}

class EventCalendarFragment : Fragment() {

    private lateinit var viewModel: EventCalendarViewModel
    private lateinit var tvMonthYear: TextView
    private lateinit var btnPreviousMonth: ImageButton
    private lateinit var btnNextMonth: ImageButton
    private lateinit var calendarContainer: LinearLayout
    private lateinit var dayNamesContainer: LinearLayout
    private lateinit var recyclerViewEvents: RecyclerView

    private var currentDate = LocalDate.now()
    private lateinit var eventAdapter: EventAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[EventCalendarViewModel::class.java]
        createNotificationChannel()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_event_calendar, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize views
        tvMonthYear = view.findViewById(R.id.tvMonthYear)
        btnPreviousMonth = view.findViewById(R.id.btnPreviousMonth)
        btnNextMonth = view.findViewById(R.id.btnNextMonth)
        calendarContainer = view.findViewById(R.id.calendarContainer)
        dayNamesContainer = view.findViewById(R.id.dayNamesContainer)
        recyclerViewEvents = view.findViewById(R.id.recyclerViewEvents)

        // Setup event list
        eventAdapter = EventAdapter(listOf()) { event ->
            showEventDetailsDialog(event)
        }
        recyclerViewEvents.layoutManager = LinearLayoutManager(context)
        recyclerViewEvents.adapter = eventAdapter

        // Setup month navigation
        btnPreviousMonth.setOnClickListener {
            currentDate = currentDate.minusMonths(1)
            updateCalendar()
        }

        btnNextMonth.setOnClickListener {
            currentDate = currentDate.plusMonths(1)
            updateCalendar()
        }

        // Setup day names
        setupDayNames()

        // Observe events
        viewModel.events.observe(viewLifecycleOwner) { events ->
            updateCalendar()
        }

        // Initial calendar update
        updateCalendar()
    }

    private fun setupDayNames() {
        val dayNames = listOf("S", "M", "T", "W", "T", "F", "S")
        dayNamesContainer.removeAllViews()

        dayNames.forEach { dayName ->
            val textView = TextView(context).apply {
                text = dayName
                textSize = 14f
                gravity = Gravity.CENTER
                setTextColor(resources.getColor(R.color.muted_foreground, null))
                layoutParams = LinearLayout.LayoutParams(
                    0,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    1f
                )
            }
            dayNamesContainer.addView(textView)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun updateCalendar() {
        // Update month/year text
        val formatter = DateTimeFormatter.ofPattern("MMMM yyyy")
        tvMonthYear.text = currentDate.format(formatter)

        // Build calendar
        buildCalendar()

        // Update events list for current month
        viewModel.events.value?.let { events ->
            val monthEvents = events.filter { event ->
                event.date.year == currentDate.year && event.date.month == currentDate.month
            }.sortedBy { it.date }

            eventAdapter = EventAdapter(monthEvents) { event ->
                showEventDetailsDialog(event)
            }
            recyclerViewEvents.adapter = eventAdapter
        }
    }

    private fun buildCalendar() {
        calendarContainer.removeAllViews()

        val yearMonth = YearMonth.from(currentDate)
        val firstDayOfMonth = yearMonth.atDay(1)
        val lastDayOfMonth = yearMonth.atEndOfMonth()
        val daysOfMonth = (1..lastDayOfMonth.dayOfMonth).map { yearMonth.atDay(it) }

        // Force week to start on Sunday to match header (S, M, T, W, T, F, S)
        // java.time DayOfWeek: MONDAY=1..SUNDAY=7 -> convert SUNDAY to index 0
        val startingOffset = firstDayOfMonth.dayOfWeek.value % 7

        // Create 6 weeks
        for (week in 0 until 6) {
            val weekRow = createWeekRow()

            for (dayOffset in 0 until 7) {
                val dayIndex = week * 7 + dayOffset - startingOffset
                val day = if (dayIndex >= 0 && dayIndex < daysOfMonth.size) {
                    daysOfMonth[dayIndex]
                } else null

                val dayView = createDayView(day)
                weekRow.addView(dayView)
            }

            calendarContainer.addView(weekRow)
        }
    }

    private fun createWeekRow(): LinearLayout {
        return LinearLayout(context).apply {
            orientation = LinearLayout.HORIZONTAL
            layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        }
    }

    private fun Int.dpToPx(context: Context): Int =
        (this * context.resources.displayMetrics.density).toInt()

    private fun createDayView(day: LocalDate?): View {
        val textView = TextView(context).apply {
            layoutParams = LinearLayout.LayoutParams(
                0,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                1f
            ).apply {
                setMargins(
                    4.dpToPx(context),
                    4.dpToPx(context),
                    4.dpToPx(context),
                    4.dpToPx(context)
                )
            }
            gravity = Gravity.CENTER
            setPadding(
                8.dpToPx(context),
                8.dpToPx(context),
                8.dpToPx(context),
                8.dpToPx(context)
            )

            if (day != null) {
                text = "${day.dayOfMonth}"
                textSize = 16f

                if (day == LocalDate.now()) {
                    setTextColor(resources.getColor(R.color.white, null))
                    background = resources.getDrawable(R.drawable.bg_calendar_day, null)
                    isSelected = true
                } else {
                    setTextColor(resources.getColor(R.color.foreground, null))
                }

                setOnClickListener {
                    handleDayClick(day)
                }
            }
        }

        // Keep numbers visible; subtly highlight days with events
        val events = viewModel.events.value ?: emptyList()
        if (day != null && events.any { it.date == day }) {
            // Use primary color to hint there is an event, but keep the number
            textView.setTextColor(resources.getColor(R.color.primary, null))
        }

        return textView
    }

    private fun handleDayClick(day: LocalDate) {
        val events = viewModel.events.value ?: emptyList()
        val dayEvents = events.filter { it.date == day }

        if (dayEvents.isEmpty()) {
            // Add new event
            showAddEventDialog(day)
        } else if (dayEvents.size == 1) {
            // Single event: show actions (view/edit/delete/add to calendar)
            showEventActions(dayEvents.first())
        } else {
            // Multiple events: pick then show actions
            showEventSelectionDialog(dayEvents)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun showAddEventDialog(selectedDate: LocalDate) {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_add_event, null)

        val etTitle = dialogView.findViewById<EditText>(R.id.etEventTitle)
        val etDescription = dialogView.findViewById<EditText>(R.id.etDescription)
        val spCategory = dialogView.findViewById<Spinner>(R.id.spCategory)
        val etLocation = dialogView.findViewById<EditText>(R.id.etLocation)
        val etStartTime = dialogView.findViewById<EditText>(R.id.etStartTime)
        val etEndTime = dialogView.findViewById<EditText>(R.id.etEndTime)
        val switchRSVP = dialogView.findViewById<Switch>(R.id.switchRSVP)
        val containerRSVPExtras = dialogView.findViewById<View>(R.id.containerRSVPExtras)
        val etMaxAttendees = dialogView.findViewById<EditText>(R.id.etMaxAttendees)

        // Setup category spinner
        val categories = EventCategory.values().map { it.displayName }
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, categories)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spCategory.adapter = adapter

        // Setup time pickers
        etStartTime.setOnClickListener {
            showTimePicker(etStartTime)
        }
        etEndTime.setOnClickListener {
            showTimePicker(etEndTime)
        }

        // Toggle max attendees visibility based on RSVP switch
        switchRSVP.setOnCheckedChangeListener { _, isChecked ->
            containerRSVPExtras.visibility = if (isChecked) View.VISIBLE else View.GONE
        }

        AlertDialog.Builder(context ?: return)
            .setTitle("Add New Event")
            .setView(dialogView)
            .setPositiveButton("Add") { _, _ ->
                val title = etTitle.text.toString()
                val description = etDescription.text.toString()
                val category = EventCategory.values()[spCategory.selectedItemPosition]
                val location = etLocation.text.toString()
                val startTimeStr = etStartTime.text.toString()
                val endTimeStr = etEndTime.text.toString()

                if (title.isNotEmpty() && startTimeStr.isNotEmpty() && endTimeStr.isNotEmpty()) {
                    try {
                        val startTime = LocalTime.parse(startTimeStr, DateTimeFormatter.ofPattern("h:mm a"))
                        val endTime = LocalTime.parse(endTimeStr, DateTimeFormatter.ofPattern("h:mm a"))

                        val maxAttendees = if (switchRSVP.isChecked) {
                            etMaxAttendees.text?.toString()?.trim()?.takeIf { it.isNotEmpty() }?.toIntOrNull()
                        } else null

                        val newEvent = Event(
                            id = UUID.randomUUID().toString(),
                            title = title,
                            description = description,
                            date = selectedDate,
                            startTime = startTime,
                            endTime = endTime,
                            location = location.ifEmpty { "No location specified" },
                            category = category,
                            rsvpRequired = switchRSVP.isChecked,
                            rsvpCount = 0,
                            maxAttendees = maxAttendees
                        )

                        viewModel.addEvent(newEvent)
                        Toast.makeText(context, "Event added successfully", Toast.LENGTH_SHORT).show()
                    } catch (e: Exception) {
                        Toast.makeText(context, "Invalid time format. Use: h:mm a", Toast.LENGTH_LONG).show()
                    }
                } else {
                    Toast.makeText(context, "Please fill in all required fields", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun showTimePicker(editText: EditText) {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        TimePickerDialog(
            context,
            { _, selectedHour, selectedMinute ->
                val time = String.format(Locale.getDefault(), "%d:%02d %s",
                    if (selectedHour > 12) selectedHour - 12 else if (selectedHour == 0) 12 else selectedHour,
                    selectedMinute,
                    if (selectedHour >= 12) "PM" else "AM")
                editText.setText(time)
            },
            hour,
            minute,
            false
        ).show()
    }

    private fun showEventSelectionDialog(events: List<Event>) {
        val eventTitles = events.map { it.title }.toTypedArray()

        AlertDialog.Builder(context ?: return)
            .setTitle("Select Event")
            .setItems(eventTitles) { _, which ->
                showEventActions(events[which])
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun showEventActions(event: Event) {
        val actions = arrayOf("View details", "Edit", "Delete", "Add to Calendar")
        AlertDialog.Builder(context ?: return)
            .setTitle(event.title)
            .setItems(actions) { _, which ->
                when (which) {
                    0 -> showEventDetailsDialog(event)
                    1 -> showEditEventDialog(event)
                    2 -> confirmDeleteEvent(event)
                    3 -> addEventToCalendar(event)
                }
            }
            .setNegativeButton("Close", null)
            .show()
    }

    private fun showEditEventDialog(event: Event) {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_add_event, null)

        val etTitle = dialogView.findViewById<EditText>(R.id.etEventTitle)
        val etDescription = dialogView.findViewById<EditText>(R.id.etDescription)
        val spCategory = dialogView.findViewById<Spinner>(R.id.spCategory)
        val etLocation = dialogView.findViewById<EditText>(R.id.etLocation)
        val etStartTime = dialogView.findViewById<EditText>(R.id.etStartTime)
        val etEndTime = dialogView.findViewById<EditText>(R.id.etEndTime)
        val switchRSVP = dialogView.findViewById<Switch>(R.id.switchRSVP)
        val containerRSVPExtras = dialogView.findViewById<View>(R.id.containerRSVPExtras)
        val etMaxAttendees = dialogView.findViewById<EditText>(R.id.etMaxAttendees)

        // Prefill
        etTitle.setText(event.title)
        etDescription.setText(event.description)
        etLocation.setText(event.location)
        switchRSVP.isChecked = event.rsvpRequired
        containerRSVPExtras.visibility = if (event.rsvpRequired) View.VISIBLE else View.GONE
        etMaxAttendees.setText(event.maxAttendees?.toString() ?: "")
        etStartTime.setText(event.startTime.format(DateTimeFormatter.ofPattern("h:mm a")))
        etEndTime.setText(event.endTime.format(DateTimeFormatter.ofPattern("h:mm a")))

        val categories = EventCategory.values().map { it.displayName }
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, categories)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spCategory.adapter = adapter
        spCategory.setSelection(EventCategory.values().indexOf(event.category))

        etStartTime.setOnClickListener { showTimePicker(etStartTime) }
        etEndTime.setOnClickListener { showTimePicker(etEndTime) }
        switchRSVP.setOnCheckedChangeListener { _, isChecked ->
            containerRSVPExtras.visibility = if (isChecked) View.VISIBLE else View.GONE
        }

        AlertDialog.Builder(context ?: return)
            .setTitle("Edit Event")
            .setView(dialogView)
            .setPositiveButton("Save") { _, _ ->
                try {
                    val maxAttendees = if (switchRSVP.isChecked) {
                        etMaxAttendees.text?.toString()?.trim()?.takeIf { it.isNotEmpty() }?.toIntOrNull()
                    } else null
                    val updated = event.copy(
                        title = etTitle.text.toString(),
                        description = etDescription.text.toString(),
                        location = etLocation.text.toString(),
                        category = EventCategory.values()[spCategory.selectedItemPosition],
                        startTime = LocalTime.parse(etStartTime.text.toString(), DateTimeFormatter.ofPattern("h:mm a")),
                        endTime = LocalTime.parse(etEndTime.text.toString(), DateTimeFormatter.ofPattern("h:mm a")),
                        rsvpRequired = switchRSVP.isChecked,
                        maxAttendees = maxAttendees
                    )
                    viewModel.updateEvent(updated)
                    Toast.makeText(context, "Event updated", Toast.LENGTH_SHORT).show()
                } catch (_: Exception) {
                    Toast.makeText(context, "Invalid time format", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun confirmDeleteEvent(event: Event) {
        AlertDialog.Builder(context ?: return)
            .setTitle("Delete Event")
            .setMessage("Are you sure you want to delete '${event.title}'?")
            .setPositiveButton("Delete") { _, _ ->
                viewModel.deleteEvent(event)
                Toast.makeText(context, "Event deleted", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    @SuppressLint("SetTextI18n")
    private fun showEventDetailsDialog(event: Event) {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_event_details, null)

        // Populate dialog
        dialogView.findViewById<TextView>(R.id.tvEventTitle).text = event.title
        dialogView.findViewById<TextView>(R.id.tvCategoryBadge).text = event.category.displayName

        val categoryBadge = dialogView.findViewById<TextView>(R.id.tvCategoryBadge)
        categoryBadge.text = event.category.displayName
        categoryBadge.setTextColor(event.category.color.toInt())

        // Set background color based on category
        val categoryColors = mapOf(
            0xFF10B981L to "#D1FAE5", // Green
            0xFF0052CCL to "#DBEAFE", // Blue
            0xFFF59E0BL to "#FEF3C7", // Orange
            0xFF6B7280L to "#F3F4F6"  // Gray
        )
        categoryBadge.setBackgroundColor(parseColor(categoryColors[event.category.color] ?: "#FFFFFF"))

        dialogView.findViewById<TextView>(R.id.tvDescription).text = event.description

        val dateFormatter = DateTimeFormatter.ofPattern("EEEE, MMMM d, yyyy")
        dialogView.findViewById<TextView>(R.id.tvDialogDate).text = event.date.format(dateFormatter)

        val timeFormatter = DateTimeFormatter.ofPattern("h:mm a")
        val timeText = "${event.startTime.format(timeFormatter)} - ${event.endTime.format(timeFormatter)}"
        dialogView.findViewById<TextView>(R.id.tvDialogTime).text = timeText

        dialogView.findViewById<TextView>(R.id.tvDialogLocation).text = event.location

        // RSVP section
        var isRSVPed = false
        var hasReminder = false
        val rsvpCount = event.rsvpCount

        if (event.rsvpRequired) {
            dialogView.findViewById<View>(R.id.containerRSVP).visibility = View.VISIBLE
            val rsvpText = if (event.maxAttendees != null) {
                "$rsvpCount / ${event.maxAttendees} attending"
            } else {
                "$rsvpCount attending"
            }
            dialogView.findViewById<TextView>(R.id.tvDialogRSVPCount).text = rsvpText

            val isFull = event.maxAttendees != null && rsvpCount >= event.maxAttendees
            if (isFull && !isRSVPed) {
                dialogView.findViewById<View>(R.id.cardFullWarning).visibility = View.VISIBLE
            }
        }

        // Reminder toggle
        val switchReminder = dialogView.findViewById<Switch>(R.id.switchReminder)
        val ivReminderIcon = dialogView.findViewById<ImageView>(R.id.ivReminderIcon)
        val tvReminderTitle = dialogView.findViewById<TextView>(R.id.tvReminderTitle)
        val tvReminderSubtitle = dialogView.findViewById<TextView>(R.id.tvReminderSubtitle)

        switchReminder.setOnCheckedChangeListener { _, isChecked ->
            hasReminder = isChecked
            if (isChecked) {
                scheduleNotification(event)
            }
            updateReminderUI(dialogView, isChecked)
        }

        updateReminderUI(dialogView, hasReminder)

        // Dialog builder
        val builder = AlertDialog.Builder(context ?: return)
            .setView(dialogView)

        if (event.rsvpRequired && !isRSVPed && !(event.maxAttendees != null && rsvpCount >= event.maxAttendees)) {
            builder.setPositiveButton("RSVP") { _, _ ->
                val updatedEvent = event.copy(rsvpCount = event.rsvpCount + 1)
                viewModel.updateEvent(updatedEvent)
                showRSVPConfirmation(updatedEvent, hasReminder)
            }
        }

        builder.setNeutralButton("Add to Calendar") { _, _ ->
            addEventToCalendar(event)
        }

        builder.setNegativeButton("Close", null)

        builder.show()
    }

    private fun updateReminderUI(dialogView: View, enabled: Boolean) {
        val ivReminderIcon = dialogView.findViewById<ImageView>(R.id.ivReminderIcon)
        val tvReminderTitle = dialogView.findViewById<TextView>(R.id.tvReminderTitle)
        val tvReminderSubtitle = dialogView.findViewById<TextView>(R.id.tvReminderSubtitle)

        if (enabled) {
            tvReminderTitle.text = "Reminder Set"
            tvReminderSubtitle.text = "You'll be notified 24 hours before"
            ivReminderIcon.setColorFilter(resources.getColor(R.color.primary, null))
        } else {
            tvReminderTitle.text = "Set Reminder"
            tvReminderSubtitle.text = "Get notified before the event"
            ivReminderIcon.setColorFilter(resources.getColor(R.color.muted_foreground, null))
        }
    }

    private fun showRSVPConfirmation(event: Event, hasReminder: Boolean) {
        val message = buildString {
            append("You've successfully RSVPed to ${event.title}")
            if (hasReminder) {
                append("\n✓ Reminder is also set")
            }
        }

        AlertDialog.Builder(context ?: return)
            .setTitle("RSVP Confirmed!")
            .setMessage(message)
            .setPositiveButton("OK", null)
            .show()
    }

    private fun parseColor(colorString: String): Int {
        return Color.parseColor(colorString)
    }

    private fun addEventToCalendar(event: Event) {
        try {
            val beginTime = event.date.atTime(event.startTime).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
            val endTime = event.date.atTime(event.endTime).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()

            val intent = Intent(Intent.ACTION_INSERT)
                .setData(CalendarContract.Events.CONTENT_URI)
                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime)
                .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime)
                .putExtra(CalendarContract.Events.TITLE, event.title)
                .putExtra(CalendarContract.Events.DESCRIPTION, event.description)
                .putExtra(CalendarContract.Events.EVENT_LOCATION, event.location)

            startActivity(intent)
            Toast.makeText(context, "Adding to calendar...", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(context, "Could not add to calendar", Toast.LENGTH_SHORT).show()
        }
    }

    private fun scheduleNotification(event: Event) {
        val notificationTime = event.date.atTime(event.startTime).minusHours(24)
        val now = LocalDateTime.now()

        if (notificationTime.isBefore(now)) {
            // Event is within 24 hours, schedule immediately
            showNotification(event)
        } else {
            // Schedule for 24 hours before
            val delayMillis = Duration.between(now, notificationTime).toMillis()

            val pendingIntent = createPendingIntent(event)

            try {
                (context?.getSystemService(Context.ALARM_SERVICE) as? AlarmManager)?.let { alarmManager ->
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (alarmManager.canScheduleExactAlarms()) {
                            alarmManager.setExactAndAllowWhileIdle(
                                AlarmManager.RTC_WAKEUP,
                                System.currentTimeMillis() + delayMillis,
                                pendingIntent
                            )
                        } else {
                            // Fallback for devices that don't allow exact alarms
                            alarmManager.set(AlarmManager.RTC_WAKEUP,
                                System.currentTimeMillis() + delayMillis,
                                pendingIntent)
                        }
                    } else {
                        alarmManager.setExact(
                            AlarmManager.RTC_WAKEUP,
                            System.currentTimeMillis() + delayMillis,
                            pendingIntent
                        )
                    }
                }
            } catch (e: SecurityException) {
                // Handle SecurityException
                Toast.makeText(context, "Could not schedule notification", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showNotification(event: Event) {
        val notificationManager = context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(
            context, 0, intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(requireContext(), "event_reminders")
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle("Event Reminder: ${event.title}")
            .setContentText(event.description)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(event.id.hashCode(), notification)
    }

    private fun createPendingIntent(event: Event): PendingIntent {
        val intent = Intent(context, NotificationReceiver::class.java).apply {
            putExtra("event_title", event.title)
            putExtra("event_description", event.description)
        }
        return PendingIntent.getBroadcast(
            context,
            event.id.hashCode(),
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "event_reminders",
                "Event Reminders",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Notifications for event reminders"
            }

            val notificationManager = context?.getSystemService(NotificationManager::class.java)
            notificationManager?.createNotificationChannel(channel)
        }
    }
}

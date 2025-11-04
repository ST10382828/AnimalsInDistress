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
import student.projects.animalsindistress.ui.fragments.event.EventAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import android.Manifest
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import java.time.*
import java.time.format.DateTimeFormatter
import java.util.*

class EventCalendarViewModel : ViewModel() {
    private val _events = MutableLiveData<List<Event>>(listOf())
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
    private var isAdmin: Boolean = false
    private var eventsListener: ListenerRegistration? = null

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
        // Determine role
        val uid = FirebaseAuth.getInstance().currentUser?.uid
        if (uid != null) {
            Firebase.firestore.collection("users").document(uid).get()
                .addOnSuccessListener { snap ->
                    isAdmin = (snap.getString("role") == "admin")
                }
        }
    }

    override fun onStart() {
        super.onStart()
        // Listen for events from Firestore
        eventsListener = Firebase.firestore.collection("events")
            .addSnapshotListener { snapshots, _ ->
                val list = snapshots?.documents?.mapNotNull { doc ->
                    try {
                        val id = doc.getString("id") ?: doc.id
                        val title = doc.getString("title") ?: return@mapNotNull null
                        val description = doc.getString("description") ?: ""
                        val dateStr = doc.getString("date") ?: return@mapNotNull null
                        val startStr = doc.getString("startTime") ?: return@mapNotNull null
                        val endStr = doc.getString("endTime") ?: return@mapNotNull null
                        val location = doc.getString("location") ?: ""
                        val categoryName = doc.getString("category") ?: EventCategory.GENERAL.name
                        val rsvpRequired = doc.getBoolean("rsvpRequired") ?: false
                        val rsvpCount = (doc.getLong("rsvpCount") ?: 0L).toInt()
                        val maxAttendees = doc.getLong("maxAttendees")?.toInt()

                        val date = LocalDate.parse(dateStr)
                        val startTime = LocalTime.parse(startStr, DateTimeFormatter.ofPattern("HH:mm"))
                        val endTime = LocalTime.parse(endStr, DateTimeFormatter.ofPattern("HH:mm"))
                        val category = EventCategory.valueOf(categoryName)

                        Event(
                            id = id,
                            title = title,
                            description = description,
                            date = date,
                            startTime = startTime,
                            endTime = endTime,
                            location = location,
                            category = category,
                            rsvpRequired = rsvpRequired,
                            rsvpCount = rsvpCount,
                            maxAttendees = maxAttendees
                        )
                    } catch (_: Exception) { null }
                }?.sortedWith(compareBy<Event> { it.date }.thenBy { it.startTime }) ?: emptyList()

                // Update ViewModel's internal LiveData
                try {
                    val field = EventCalendarViewModel::class.java.getDeclaredField("_events")
                    field.isAccessible = true
                    @Suppress("UNCHECKED_CAST")
                    (field.get(viewModel) as MutableLiveData<List<Event>>).value = list
                } catch (_: Exception) { }
            }
    }

    override fun onStop() {
        super.onStop()
        eventsListener?.remove()
        eventsListener = null
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
            // Add new event (admin only)
            if (isAdmin) showAddEventDialog(day)
        } else if (dayEvents.size == 1) {
            // Single event: show actions (role-based)
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

                        if (isAdmin) {
                            val doc = Firebase.firestore.collection("events").document()
                            val id = doc.id
                            val timeFmt = DateTimeFormatter.ofPattern("HH:mm")
                            val data = hashMapOf(
                                "id" to id,
                                "title" to newEvent.title,
                                "description" to newEvent.description,
                                "date" to selectedDate.toString(),
                                "startTime" to startTime.format(timeFmt),
                                "endTime" to endTime.format(timeFmt),
                                "location" to newEvent.location,
                                "category" to category.name,
                                "rsvpRequired" to switchRSVP.isChecked,
                                "rsvpCount" to 0,
                                "maxAttendees" to maxAttendees
                            )
                            doc.set(data)
                        }
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
        val actions = if (isAdmin) arrayOf("View details", "Edit", "Delete", "Add to Calendar") else arrayOf("View details", "Add to Calendar")
        AlertDialog.Builder(context ?: return)
            .setTitle(event.title)
            .setItems(actions) { _, which ->
                if (isAdmin) {
                    when (which) {
                        0 -> showEventDetailsDialog(event)
                        1 -> showEditEventDialog(event)
                        2 -> confirmDeleteEvent(event)
                        3 -> addEventToCalendar(event)
                    }
                } else {
                    when (which) {
                        0 -> showEventDetailsDialog(event)
                        1 -> addEventToCalendar(event)
                    }
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
                    if (isAdmin) {
                        val category = EventCategory.values()[spCategory.selectedItemPosition]
                        val startTime = LocalTime.parse(etStartTime.text.toString(), DateTimeFormatter.ofPattern("h:mm a"))
                        val endTime = LocalTime.parse(etEndTime.text.toString(), DateTimeFormatter.ofPattern("h:mm a"))
                        val timeFmt = DateTimeFormatter.ofPattern("HH:mm")
                        val updates = mapOf(
                            "title" to etTitle.text.toString(),
                            "description" to etDescription.text.toString(),
                            "location" to etLocation.text.toString(),
                            "category" to category.name,
                            "startTime" to startTime.format(timeFmt),
                            "endTime" to endTime.format(timeFmt),
                            "rsvpRequired" to switchRSVP.isChecked,
                            "maxAttendees" to maxAttendees
                        )
                        Firebase.firestore.collection("events").document(event.id).update(updates)
                            .addOnSuccessListener { Toast.makeText(context, "Event updated", Toast.LENGTH_SHORT).show() }
                            .addOnFailureListener { Toast.makeText(context, "Update failed", Toast.LENGTH_SHORT).show() }
                    }
                } catch (_: Exception) {
                    Toast.makeText(context, "Invalid time format", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun confirmDeleteEvent(event: Event) {
        if (!isAdmin) return
        AlertDialog.Builder(context ?: return)
            .setTitle("Delete Event")
            .setMessage("Are you sure you want to delete '${event.title}'?")
            .setPositiveButton("Delete") { _, _ ->
                Firebase.firestore.collection("events").document(event.id).delete()
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

        val currentUid = FirebaseAuth.getInstance().currentUser?.uid
        if (currentUid != null) {
            // Load persisted reminder state for this user/event
            Firebase.firestore.collection("events").document(event.id)
                .collection("reminders").document(currentUid).get()
                .addOnSuccessListener { snap ->
                    hasReminder = snap.exists()
                    switchReminder.isChecked = hasReminder
                    updateReminderUI(dialogView, hasReminder)
                }
        } else {
            updateReminderUI(dialogView, hasReminder)
        }

        switchReminder.setOnCheckedChangeListener { _, isChecked ->
            hasReminder = isChecked
            val uid = FirebaseAuth.getInstance().currentUser?.uid
            if (uid == null) {
                switchReminder.isChecked = false
                Toast.makeText(requireContext(), "Please login to set reminders", Toast.LENGTH_SHORT).show()
            } else {
                val reminders = Firebase.firestore.collection("events").document(event.id).collection("reminders").document(uid)
                if (isChecked) {
                    reminders.set(mapOf("uid" to uid, "createdAt" to java.util.Date()))
                    // Request notification permission on Android 13+
                    if (Build.VERSION.SDK_INT >= 33 && ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(arrayOf(Manifest.permission.POST_NOTIFICATIONS), 1001)
                        Toast.makeText(requireContext(), "Please allow notifications to receive reminders", Toast.LENGTH_LONG).show()
                    }
                    // Prompt for exact alarm policy if blocked (Android 12+)
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                        val am = context?.getSystemService(Context.ALARM_SERVICE) as? AlarmManager
                        if (am != null && !am.canScheduleExactAlarms()) {
                            try {
                                startActivity(Intent(android.provider.Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM))
                            } catch (_: Exception) { }
                        }
                    }
                    scheduleNotification(event, uid)
                } else {
                    reminders.delete()
                    cancelScheduledNotification(event, uid)
                }
                updateReminderUI(dialogView, isChecked)
            }
        }

        // Dialog builder
        val builder = AlertDialog.Builder(context ?: return)
            .setView(dialogView)
            .setPositiveButton("RSVP", null)

        builder.setNeutralButton("Add to Calendar") { _, _ ->
            addEventToCalendar(event)
        }

        builder.setNegativeButton("Close", null)

        val alert = builder.create()
        alert.show()

        // Configure RSVP/Un-RSVP after dialog shows so we can modify the button
        val uid = FirebaseAuth.getInstance().currentUser?.uid
        val email = FirebaseAuth.getInstance().currentUser?.email
        val positive = alert.getButton(AlertDialog.BUTTON_POSITIVE)

        if (event.rsvpRequired) {
            if (uid == null) {
                // Not logged in: RSVP prompts login
                positive.text = "RSVP"
                positive.setOnClickListener {
                    Toast.makeText(requireContext(), "Please login to RSVP", Toast.LENGTH_SHORT).show()
                }
            } else {
                val db = Firebase.firestore
                val eventRef = db.collection("events").document(event.id)
                val rsvpRef = eventRef.collection("rsvps").document(uid)
                rsvpRef.get().addOnSuccessListener { snap ->
                    val userAlreadyRSVPed = snap.exists()
                    val isFull = event.maxAttendees != null && event.rsvpCount >= event.maxAttendees

                    if (userAlreadyRSVPed) {
                        positive.text = "Un-RSVP"
                        positive.setOnClickListener {
                            db.runTransaction { tx ->
                                val existing = tx.get(rsvpRef)
                                if (!existing.exists()) {
                                    throw IllegalStateException("not_rsvped")
                                }
                                tx.delete(rsvpRef)
                                tx.update(eventRef, "rsvpCount", FieldValue.increment(-1))
                                null
                            }.addOnSuccessListener {
                                Toast.makeText(requireContext(), "You have been removed from the RSVP", Toast.LENGTH_SHORT).show()
                                alert.dismiss()
                            }.addOnFailureListener {
                                Toast.makeText(requireContext(), "Could not un-RSVP", Toast.LENGTH_SHORT).show()
                            }
                        }
                    } else {
                        if (isFull) {
                            positive.visibility = View.GONE
                        } else {
                            positive.text = "RSVP"
                            positive.setOnClickListener {
                                db.runTransaction { tx ->
                                    val existing = tx.get(rsvpRef)
                                    if (existing.exists()) {
                                        throw IllegalStateException("already_rsvped")
                                    }
                                    val eventSnap = tx.get(eventRef)
                                    val max = eventSnap.getLong("maxAttendees")?.toInt()
                                    val count = (eventSnap.getLong("rsvpCount") ?: 0L).toInt()
                                    if (max != null && count >= max) {
                                        throw IllegalStateException("event_full")
                                    }
                                    tx.set(rsvpRef, mapOf(
                                        "uid" to uid,
                                        "email" to (email ?: ""),
                                        "createdAt" to java.util.Date()
                                    ))
                                    tx.update(eventRef, "rsvpCount", FieldValue.increment(1))
                                    null
                                }.addOnSuccessListener {
                                    showRSVPConfirmation(event.copy(rsvpCount = event.rsvpCount + 1), hasReminder)
                                    alert.dismiss()
                                }.addOnFailureListener { err ->
                                    when (err.message) {
                                        "already_rsvped" -> Toast.makeText(requireContext(), "You have already RSVPed", Toast.LENGTH_SHORT).show()
                                        "event_full" -> Toast.makeText(requireContext(), "Event is full", Toast.LENGTH_SHORT).show()
                                        else -> Toast.makeText(requireContext(), "Could not RSVP", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } else {
            // If not required, hide positive button
            positive.visibility = View.GONE
        }
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

    private fun scheduleNotification(event: Event, uid: String) {
        val notificationTime = event.date.atTime(event.startTime).minusHours(24)
        val now = LocalDateTime.now()

        if (notificationTime.isBefore(now)) {
            // Event is within 24 hours, schedule immediately
            showNotification(event)
        } else {
            // Schedule for 24 hours before
            val delayMillis = Duration.between(now, notificationTime).toMillis()

            val pendingIntent = createPendingIntent(event, uid)

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

    private fun cancelScheduledNotification(event: Event, uid: String) {
        val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as? AlarmManager ?: return
        val pendingIntent = createPendingIntent(event, uid)
        alarmManager.cancel(pendingIntent)
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

    private fun createPendingIntent(event: Event, uid: String): PendingIntent {
        val intent = Intent(context, NotificationReceiver::class.java).apply {
            putExtra("event_title", event.title)
            val timeFormatter = DateTimeFormatter.ofPattern("h:mm a")
            val dateFormatter = DateTimeFormatter.ofPattern("EEE, MMM d")
            val timeText = "at ${event.startTime.format(timeFormatter)} on ${event.date.format(dateFormatter)}"
            putExtra("event_time_text", timeText)
            putExtra("event_description", event.description)
            putExtra("event_id", event.id)
            putExtra("uid", uid)
        }
        // Make requestCode unique per (event, user)
        val requestCode = (event.id + ":" + uid).hashCode()
        return PendingIntent.getBroadcast(
            context,
            requestCode,
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

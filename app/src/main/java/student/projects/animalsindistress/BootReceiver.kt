package student.projects.animalsindistress

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.time.*
import java.time.format.DateTimeFormatter

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        try { if (FirebaseApp.getApps(context).isEmpty()) FirebaseApp.initializeApp(context) } catch (_: Exception) { }
        val user = FirebaseAuth.getInstance().currentUser ?: return
        val db = Firebase.firestore
        db.collectionGroup("reminders").whereEqualTo("uid", user.uid).get()
            .addOnSuccessListener { snaps ->
                snaps.documents.forEach { reminderDoc ->
                    val eventRef = reminderDoc.reference.parent.parent ?: return@forEach
                    eventRef.get().addOnSuccessListener { eventSnap ->
                        val id = eventRef.id
                        val title = eventSnap.getString("title") ?: return@addOnSuccessListener
                        val description = eventSnap.getString("description") ?: ""
                        val dateStr = eventSnap.getString("date") ?: return@addOnSuccessListener
                        val startStr = eventSnap.getString("startTime") ?: return@addOnSuccessListener
                        try {
                            val date = LocalDate.parse(dateStr)
                            val start = LocalTime.parse(startStr, DateTimeFormatter.ofPattern("HH:mm"))
                            schedule(context, id, title, description, date, start, user.uid)
                        } catch (_: Exception) { }
                    }
                }
            }
    }

    private fun schedule(context: Context, eventId: String, title: String, description: String, date: LocalDate, startTime: LocalTime, uid: String) {
        val trigger = date.atTime(startTime).minusHours(24)
        val now = LocalDateTime.now()
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as? AlarmManager ?: return
        val intent = Intent(context, NotificationReceiver::class.java).apply {
            putExtra("event_title", title)
            val timeFormatter = DateTimeFormatter.ofPattern("h:mm a")
            val dateFormatter = DateTimeFormatter.ofPattern("EEE, MMM d")
            val timeText = "at ${startTime.format(timeFormatter)} on ${date.format(dateFormatter)}"
            putExtra("event_time_text", timeText)
            putExtra("event_description", description)
            putExtra("event_id", eventId)
            putExtra("uid", uid)
        }
        val requestCode = (eventId + ":" + uid).hashCode()
        val pi = PendingIntent.getBroadcast(context, requestCode, intent, PendingIntent.FLAG_IMMUTABLE)
        val triggerAt = if (trigger.isBefore(now)) System.currentTimeMillis() + 5_000 else trigger.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            try { alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, triggerAt, pi) } catch (_: SecurityException) { }
        } else {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, triggerAt, pi)
        }
    }
}



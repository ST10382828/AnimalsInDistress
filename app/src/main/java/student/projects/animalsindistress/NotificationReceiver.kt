package student.projects.animalsindistress

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import kotlin.apply
import kotlin.jvm.java

class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val title = intent.getStringExtra("event_title") ?: "Event Reminder"
        val timeText = intent.getStringExtra("event_time_text") ?: "soon"
        val description = intent.getStringExtra("event_description") ?: ""

        val notificationIntent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(
            context, 0, notificationIntent,
            PendingIntent.FLAG_IMMUTABLE
        )

        // Ensure channel exists (in case app process not initialized)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val manager = context.getSystemService(NotificationManager::class.java)
            val channel = NotificationChannel(
                "event_reminders",
                "Event Reminders",
                NotificationManager.IMPORTANCE_HIGH
            )
            manager?.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(context, "event_reminders")
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle("Reminder: $title")
            .setContentText("You have $title $timeText")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        NotificationManagerCompat.from(context).notify(title.hashCode(), notification)
    }
}


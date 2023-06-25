package com.albuquerque.common.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.albuquerque.analytics.EventName
import com.albuquerque.analytics.EventTracker
import com.albuquerque.analytics.PropertiesName
import com.albuquerque.common.R
import com.albuquerque.common.deeplink.CinemaDeeplink
import org.koin.java.KoinJavaComponent.inject

private const val CHANNEL_ID = "ReminderNotificationChannel"

class CinemaNotificationManagerImpl(
    private val context: Context?
) : CinemaNotificationManager {

    private val tracker by inject<EventTracker>(EventTracker::class.java)

    override fun buildNotification(deeplink: String?, message: String, notificationId: Int) {
        if (context == null) return

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_ID,
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Reminder Channel Description"
            }
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

        val intent = Intent().apply {
            action = Intent.ACTION_VIEW
            data = if (deeplink == null) Uri.parse(CinemaDeeplink.Home.url) else Uri.parse(deeplink)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent = PendingIntent.getActivity(
            context,
            notificationId,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setAutoCancel(true)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(
                context.resources.getString(R.string.common_title_notification)
            )
            .setContentText(
                message
            )
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        tracker.track(EventName.Notification.Received.value) {
            properties {
                PropertiesName.Notification.Text.value withValue message
                PropertiesName.Notification.Status.value withValue notificationId
            }
        }

        NotificationManagerCompat.from(context).notify(notificationId, notification)
    }
}
package com.albuquerque.data.local.work

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters

internal class ReminderMovieWorker(
    val context: Context,
    val params: WorkerParameters
) : Worker(context, params) {

    companion object {
        private const val CHANNEL_ID = "ReminderNotificationChannel"
    }

    override fun doWork(): Result {
        try {
            createNotification(
                message = inputData.getString("message").orEmpty(),
                deeplink = inputData.getString("deeplink").orEmpty(),
                notificationId = inputData.getString("notificationId").orEmpty().hashCode()
            )
        } catch (_: Exception) {}

        return Result.success()
    }
    private fun createNotification(message: String, deeplink: String, notificationId: Int) {
        createNotificationChannel()

        val intent = Intent().apply {
            action = Intent.ACTION_VIEW
            data = Uri.parse(deeplink)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setAutoCancel(true)
            .setSmallIcon(com.albuquerque.common.R.drawable.ic_movie_notification)
            .setContentTitle(
                context.resources.getString(com.albuquerque.common.R.string.common_title_notification)
            )
            .setContentText(
                message
            )
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        NotificationManagerCompat.from(context).notify(notificationId, notification)
    }

    private fun createNotificationChannel() {
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
    }

}
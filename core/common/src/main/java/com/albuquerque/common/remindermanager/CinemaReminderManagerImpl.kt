package com.albuquerque.common.remindermanager

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import com.albuquerque.common.broadcast.MovieReminderBroadcast
import com.albuquerque.common.model.MovieReminderDay

class CinemaReminderManagerImpl(
    private val context: Context
) : CinemaReminderManager {

    override fun createNotification(
        reminderDay: Int,
        movieName: String,
        movieId: Int,
        deeplink: String,
        timeInMillis: Long
    ) {
        val alarmManager = context.getSystemService(AlarmManager::class.java)

        val messageRes = when (reminderDay) {
            MovieReminderDay.D0.day -> com.albuquerque.common.R.string.common_message_notification_D0
            MovieReminderDay.D_MINUS_2.day -> com.albuquerque.common.R.string.common_message_notification_D_minus_2
            MovieReminderDay.D_MINUS_6.day -> com.albuquerque.common.R.string.common_message_notification_D_minus_6
            else -> 0
        }

        val intent = Intent(context, MovieReminderBroadcast::class.java).apply {
            putExtra("deeplink", deeplink)
            putExtra("message", context.resources.getString(messageRes, movieName))
            putExtra("notificationId", movieId)
            putExtra("reminderDay", reminderDay)
        }

        val pendingIntent = context.getReminderBroadcast(intent, movieId)

        alarmManager.set(
            AlarmManager.RTC_WAKEUP,
            timeInMillis,
            pendingIntent
        )
    }

    override fun deleteNotification(movieId: Int) {
        val alarmManager = context.getSystemService(AlarmManager::class.java)

        val intent = Intent(context, MovieReminderBroadcast::class.java).apply {
            putExtra("notificationId", movieId)
        }

        val pendingIntent = context.getReminderBroadcast(intent, movieId)

        alarmManager.cancel(pendingIntent)
    }

    private fun Context.getReminderBroadcast(intent: Intent, notificationId: Int): PendingIntent {
        return PendingIntent.getBroadcast(
            this,
            notificationId,
            intent,
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) { PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT } else { PendingIntent.FLAG_UPDATE_CURRENT }
        )
    }
}
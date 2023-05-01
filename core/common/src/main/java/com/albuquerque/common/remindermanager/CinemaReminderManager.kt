package com.albuquerque.common.remindermanager

interface CinemaReminderManager {

    fun createNotification(
        reminderDay: Int,
        movieName: String,
        movieId: Int,
        deeplink: String,
        timeInMillis: Long
    )

    fun deleteNotification(movieId: Int)
}
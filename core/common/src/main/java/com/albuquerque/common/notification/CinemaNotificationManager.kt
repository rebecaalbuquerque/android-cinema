package com.albuquerque.common.notification

interface CinemaNotificationManager {
    fun buildNotification(deeplink: String?, message: String, notificationId: Int = 0)
}
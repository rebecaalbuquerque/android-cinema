package com.albuquerque.cinema.service

import android.util.Log
import com.albuquerque.common.notification.CinemaNotificationManager
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import org.koin.java.KoinJavaComponent

class CinemaMessagingService : FirebaseMessagingService() {

    private val notificationManager by KoinJavaComponent.inject<CinemaNotificationManager>(CinemaNotificationManager::class.java)

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.d("Cinema App FCM", "onMessageReceived")

        if (remoteMessage.notification != null) {
            val body = remoteMessage.notification?.body ?: return
            val deeplink = remoteMessage.data["deeplink"]
            val notificationId = remoteMessage.data["movieId"]?.toInt() ?: 0

            Log.d("Cinema App FCM", "body: $body, deeplink: $deeplink")
            notificationManager.buildNotification(deeplink, body, notificationId)
        }
    }

    override fun onNewToken(token: String) {
        Log.d("Cinema App FCM" , "onNewToken $token")
    }
}
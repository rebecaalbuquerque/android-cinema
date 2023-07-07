package com.albuquerque.cinema.service

import android.util.Log
import com.albuquerque.auth.domain.SaveFcmTokenUseCase
import com.albuquerque.common.notification.CinemaNotificationManager
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import org.koin.java.KoinJavaComponent.inject

class CinemaMessagingService : FirebaseMessagingService() {

    private val saveFcmTokenUseCase by inject<SaveFcmTokenUseCase>(SaveFcmTokenUseCase::class.java)
    private val notificationManager by inject<CinemaNotificationManager>(CinemaNotificationManager::class.java)

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
        saveFcmTokenUseCase.invoke(token = token, isNewToken = true)
    }
}
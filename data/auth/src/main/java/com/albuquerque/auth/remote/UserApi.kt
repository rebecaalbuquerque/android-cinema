package com.albuquerque.auth.remote

interface UserApi {

    @POST("notifications/schedule")
    suspend fun scheduleNotification(
        @Body movieNotification: MovieNotificationRequest
    ): Result<Unit>
}
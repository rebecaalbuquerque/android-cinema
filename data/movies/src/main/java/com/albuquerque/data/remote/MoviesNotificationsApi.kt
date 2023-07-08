package com.albuquerque.data.remote

import com.albuquerque.data.remote.model.MovieNotificationRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface MoviesNotificationsApi {

    @POST("notifications/schedule")
    suspend fun scheduleNotification(
        @Body movieNotification: MovieNotificationRequest
    ): Result<Unit>

    @GET("notifications/unschedule")
    suspend fun unscheduleNotification()
}
package com.albuquerque.data.datasource

import com.albuquerque.core.data.remote.extension.emitRequestFlow
import com.albuquerque.core.data.remote.extension.emitRequestResult
import com.albuquerque.data.remote.MoviesApi
import com.albuquerque.data.remote.MoviesNotificationsApi
import com.albuquerque.data.remote.model.MovieListResponse
import com.albuquerque.data.remote.model.MovieNotificationRequest
import kotlinx.coroutines.flow.Flow

class MoviesRemoteDataSourceImpl(
    private val moviesApi: MoviesApi,
    private val moviesNotificationApi: MoviesNotificationsApi,
) : MoviesRemoteDataSource {

    override fun getUpcomingMovies(): Flow<MovieListResponse> = emitRequestFlow {
        moviesApi.getUpcomingMovies()
    }

    override suspend fun scheduleNotification(
        movieId: Int,
        movieName: String,
        releaseDate: String,
        deviceToken: String,
        deviceUuid: String
    ): Result<Unit> = emitRequestResult {
        moviesNotificationApi.scheduleNotification(
            MovieNotificationRequest(movieId, movieName, releaseDate, deviceToken, deviceUuid)
        )
    }
}
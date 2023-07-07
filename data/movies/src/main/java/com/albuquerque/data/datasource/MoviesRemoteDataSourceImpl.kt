package com.albuquerque.data.datasource

import com.albuquerque.data.remote.MoviesApi
import com.albuquerque.data.remote.MoviesNotificationsApi
import com.albuquerque.data.remote.model.MovieListResponse
import com.albuquerque.data.remote.model.MovieNotificationRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MoviesRemoteDataSourceImpl(
    private val moviesApi: MoviesApi,
    private val moviesNotificationApi: MoviesNotificationsApi,
) : MoviesRemoteDataSource {

    override fun getUpcomingMovies(): Flow<MovieListResponse> = flow {
        emit(moviesApi.getUpcomingMovies())
    }

    override fun scheduleNotification(
        movieId: Int,
        movieName: String,
        releaseDate: String,
        deviceToken: String,
        deviceUuid: String
    ) = flow {
        emit(
            moviesNotificationApi.scheduleNotification(
                MovieNotificationRequest(movieId, movieName, releaseDate, deviceToken, deviceUuid)
            )
        )
    }
}
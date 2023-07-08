package com.albuquerque.data.datasource

import com.albuquerque.data.remote.model.MovieListResponse
import kotlinx.coroutines.flow.Flow

interface MoviesRemoteDataSource {
    fun getUpcomingMovies(): Flow<MovieListResponse>

    suspend fun scheduleNotification(movieId: Int, movieName: String, releaseDate: String, deviceToken: String, deviceUuid: String): Result<Unit>
}
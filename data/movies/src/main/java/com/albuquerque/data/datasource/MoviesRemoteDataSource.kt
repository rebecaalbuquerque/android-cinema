package com.albuquerque.data.datasource

import com.albuquerque.data.model.MovieListResponse
import kotlinx.coroutines.flow.Flow

interface MoviesRemoteDataSource {
    fun getUpcomingMovies(): Flow<MovieListResponse>
}
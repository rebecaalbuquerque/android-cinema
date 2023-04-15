package com.albuquerque.data.datasource

import com.albuquerque.data.remote.MoviesApi
import com.albuquerque.data.remote.model.MovieListResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MoviesRemoteDataSourceImpl(
    private val api: MoviesApi
) : MoviesRemoteDataSource {

    override fun getUpcomingMovies(): Flow<MovieListResponse> = flow {
        emit(api.getUpcomingMovies())
    }
}
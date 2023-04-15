package com.albuquerque.data.repository

import com.albuquerque.data.datasource.MoviesLocalDataSource
import com.albuquerque.data.datasource.MoviesRemoteDataSource
import com.albuquerque.data.mapper.toMovieList
import com.albuquerque.domain.model.MovieList
import com.albuquerque.domain.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MoviesRepositoryImpl(
    private val remoteDataSource: MoviesRemoteDataSource,
    private val localDataSource: MoviesLocalDataSource
) : MoviesRepository {

    override fun getUpcomingMovies(): Flow<MovieList> {
        return remoteDataSource.getUpcomingMovies().map { it.toMovieList() }
    }
}
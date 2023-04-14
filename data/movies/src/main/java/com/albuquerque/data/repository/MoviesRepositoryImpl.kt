package com.albuquerque.data.repository

import com.albuquerque.data.datasource.MoviesLocalDataSource
import com.albuquerque.data.datasource.MoviesRemoteDataSource
import com.albuquerque.domain.repository.MoviesRepository

class MoviesRepositoryImpl(
    private val remoteDataSource: MoviesRemoteDataSource,
    private val localDataSource: MoviesLocalDataSource
) : MoviesRepository {
}
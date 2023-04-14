package com.albuquerque.data.datasource

import com.albuquerque.data.api.MoviesApi

class MoviesRemoteDataSourceImpl(
    private val api: MoviesApi
) : MoviesRemoteDataSource {
}
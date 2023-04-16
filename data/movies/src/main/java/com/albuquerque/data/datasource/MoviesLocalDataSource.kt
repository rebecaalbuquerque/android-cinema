package com.albuquerque.data.datasource

import com.albuquerque.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MoviesLocalDataSource {
    suspend fun updateFavorite(movie: Movie)
    fun getFavorites(): Flow<List<Movie>>
}
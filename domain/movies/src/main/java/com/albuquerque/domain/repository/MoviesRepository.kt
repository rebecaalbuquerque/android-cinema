package com.albuquerque.domain.repository

import com.albuquerque.domain.model.MovieList
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {
    fun getUpcomingMovies(): Flow<MovieList>
}
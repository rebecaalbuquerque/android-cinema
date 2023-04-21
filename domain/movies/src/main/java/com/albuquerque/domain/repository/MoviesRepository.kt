package com.albuquerque.domain.repository

import com.albuquerque.domain.model.Movie
import com.albuquerque.domain.model.MovieList
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {

    fun getUpcomingMovies(currentTimeInMillis: Long): Flow<MovieList>

    suspend fun updateMovie(movie: Movie)

    fun getFavorites(): Flow<List<Movie>>

    fun getReminders(): Flow<List<Movie>>

    fun createMovieReminder(delayReminder: Long, reminderDay: Int, movie: Movie): Flow<Unit>

    fun deleteMovieReminder(movie: Movie): Flow<Unit>
}
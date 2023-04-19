package com.albuquerque.moviesupcoming.domain.usecase

import com.albuquerque.domain.model.MovieList
import com.albuquerque.domain.repository.MoviesRepository
import java.util.Calendar
import kotlinx.coroutines.flow.Flow

class GetUpcomingMoviesUseCase(
    private val repository: MoviesRepository
) {
    operator fun invoke(): Flow<MovieList> {
        val currentTimeInMillis = Calendar.getInstance().timeInMillis
        return repository.getUpcomingMovies(currentTimeInMillis)
    }
}
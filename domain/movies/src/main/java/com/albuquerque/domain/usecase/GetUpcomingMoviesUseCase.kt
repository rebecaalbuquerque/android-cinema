package com.albuquerque.domain.usecase

import com.albuquerque.domain.model.MovieList
import com.albuquerque.domain.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow

class GetUpcomingMoviesUseCase(
    private val repository: MoviesRepository
) {
    operator fun invoke(): Flow<MovieList> = repository.getUpcomingMovies()
}
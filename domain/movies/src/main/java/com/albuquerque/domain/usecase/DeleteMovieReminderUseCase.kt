package com.albuquerque.domain.usecase

import com.albuquerque.domain.model.Movie
import com.albuquerque.domain.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow

class DeleteMovieReminderUseCase(
    private val repository: MoviesRepository
) {

    operator fun invoke(movie: Movie): Flow<Unit> {
        return repository.deleteMovieReminder(movie)
    }
}
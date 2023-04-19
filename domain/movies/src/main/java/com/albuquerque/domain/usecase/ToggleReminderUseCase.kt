package com.albuquerque.domain.usecase

import com.albuquerque.domain.model.Movie
import kotlinx.coroutines.flow.Flow

class ToggleReminderUseCase(
    private val createMovieReminderUseCase: CreateMovieReminderUseCase,
    private val deleteMovieReminderUseCase: DeleteMovieReminderUseCase
) {

    operator fun invoke(movie: Movie): Flow<Unit> {
        return if (movie.hasReminder) {
            deleteMovieReminderUseCase(
                movie.copy(hasReminder = false)
            )
        } else {
            createMovieReminderUseCase(
                movie.copy(hasReminder = true)
            )
        }
    }
}
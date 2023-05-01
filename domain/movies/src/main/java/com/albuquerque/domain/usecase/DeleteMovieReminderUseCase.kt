package com.albuquerque.domain.usecase

import com.albuquerque.domain.model.Movie
import com.albuquerque.common.model.MovieReminderDay.D_MINUS_6
import com.albuquerque.common.model.MovieReminderDay.D_MINUS_2
import com.albuquerque.common.model.MovieReminderDay.D0
import com.albuquerque.domain.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.zip

class DeleteMovieReminderUseCase(
    private val repository: MoviesRepository
) {

    operator fun invoke(movie: Movie): Flow<Unit> {
        val dMinus6Flow = repository.deleteMovieReminder(D_MINUS_6.day, movie)
        val dMinus2Flow = repository.deleteMovieReminder(D_MINUS_2.day, movie)
        val dZeroFlow = repository.deleteMovieReminder(D0.day, movie)

        return dMinus6Flow
            .zip(dMinus2Flow) { _, _ -> }
            .zip(dZeroFlow) { _ , _ -> }
    }
}
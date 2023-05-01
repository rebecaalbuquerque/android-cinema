package com.albuquerque.domain.usecase

import com.albuquerque.common.extension.orEmpty
import com.albuquerque.domain.model.Movie
import com.albuquerque.common.model.MovieReminderDay
import com.albuquerque.common.model.MovieReminderDay.D0
import com.albuquerque.common.model.MovieReminderDay.D_MINUS_2
import com.albuquerque.common.model.MovieReminderDay.D_MINUS_6
import com.albuquerque.domain.repository.MoviesRepository
import java.util.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.zip

class CreateMovieReminderUseCase(
    private val repository: MoviesRepository
) {

    operator fun invoke(movie: Movie, remindersTimeInMillis: Map<MovieReminderDay, Long>): Flow<Unit> {
        val dMinus6Flow = createMovieReminder(
            remindersTimeInMillis[D_MINUS_6].orEmpty(), D_MINUS_6.day, movie
        )
        val dMinus2Flow = createMovieReminder(
            remindersTimeInMillis[D_MINUS_2].orEmpty(), D_MINUS_2.day, movie
        )
        val dZeroFlow = createMovieReminder(
            remindersTimeInMillis[D0].orEmpty(), D0.day, movie
        )

        return dMinus6Flow
            .zip(dMinus2Flow) { _, _ -> }
            .zip(dZeroFlow) { _ , _ -> }
    }

    private fun createMovieReminder(reminderInMillis: Long, reminderDay: Int, movie: Movie): Flow<Unit> {
        if (reminderInMillis <= Calendar.getInstance().timeInMillis) return flowOf(Unit)

        return repository.createMovieReminder(
            reminderInMillis = reminderInMillis,
            reminderDay = reminderDay,
            movie = movie
        )
    }
}
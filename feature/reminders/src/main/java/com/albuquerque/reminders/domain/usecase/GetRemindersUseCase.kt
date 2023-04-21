package com.albuquerque.reminders.domain.usecase

import com.albuquerque.domain.model.Movie
import com.albuquerque.domain.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow

internal class GetRemindersUseCase(
    private val repository: MoviesRepository
) {

    operator fun invoke(): Flow<List<Movie>> {
        return repository.getReminders()
    }
}
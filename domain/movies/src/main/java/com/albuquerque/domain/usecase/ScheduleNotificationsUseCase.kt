package com.albuquerque.domain.usecase

import com.albuquerque.domain.model.Movie
import com.albuquerque.domain.repository.MoviesRepository

class ScheduleNotificationsUseCase(
    private val repository: MoviesRepository
) {

    suspend operator fun invoke(movie: Movie, deviceToken: String) {
        val previousReminderStatus = movie.reminderStatus

        try {
            repository.updateMovie(
                movie.copy(reminderStatus = Movie.ReminderStatus.TRYING_TO_SCHEDULE.name)
            )

            val scheduling = repository.scheduleNotifications(
                deviceToken,
                movie.id,
                movie.title,
                movie.releaseDate
            )

            if (scheduling.isSuccess) {
                repository.updateMovie(
                    movie.copy(reminderStatus = Movie.ReminderStatus.SCHEDULED.name)
                )
            } else {
                repository.updateMovie(
                    movie.copy(reminderStatus = previousReminderStatus)
                )
            }

        } catch (exception: Exception) {
            throw exception
        }
    }
}
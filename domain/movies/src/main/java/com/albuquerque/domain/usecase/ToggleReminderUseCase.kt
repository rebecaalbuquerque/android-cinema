package com.albuquerque.domain.usecase

import com.albuquerque.domain.model.Movie
import com.albuquerque.common.model.MovieReminderDay
import java.text.SimpleDateFormat
import java.util.*
import kotlinx.coroutines.flow.Flow

class ToggleReminderUseCase(
    private val createMovieReminderUseCase: CreateMovieReminderUseCase,
    private val deleteMovieReminderUseCase: DeleteMovieReminderUseCase
) {

    operator fun invoke(movie: Movie): Flow<Unit> {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale("pt", "BR"))
        val remindersTimeInMillis = mutableMapOf<MovieReminderDay, Long>()

        try {
            val movieReleaseDate = sdf.parse(movie.releaseDate)

            if(movieReleaseDate != null) {
                remindersTimeInMillis[MovieReminderDay.D_MINUS_6] = getReminderDateInMillis(
                    fromDate = movieReleaseDate,
                    targetDay = MovieReminderDay.D_MINUS_6
                )

                remindersTimeInMillis[MovieReminderDay.D_MINUS_2] = getReminderDateInMillis(
                    fromDate = movieReleaseDate,
                    targetDay = MovieReminderDay.D_MINUS_2
                )

                remindersTimeInMillis[MovieReminderDay.D0] = getReminderDateInMillis(
                    fromDate = movieReleaseDate,
                    targetDay = MovieReminderDay.D0
                )
            } else {
                throw Exception("Erro ao fazer parse da data de estreia do filme")
            }

        } catch (exception: Exception) {
            throw exception
        }

        return if (movie.hasReminder) {
            deleteMovieReminderUseCase(
                movie.copy(hasReminder = false)
            )
        } else {
            createMovieReminderUseCase(
                movie.copy(hasReminder = true),
                remindersTimeInMillis
            )
        }
    }

    private fun getReminderDateInMillis(
        fromDate: Date,
        targetDay: MovieReminderDay,
        targetHour: Int = 9
    ): Long {
        val calendar = Calendar.getInstance().apply {
            time = fromDate
            add(Calendar.DAY_OF_MONTH, targetDay.day)
            set(Calendar.HOUR_OF_DAY, targetHour)
        }

        return calendar.timeInMillis
    }
}
package com.albuquerque.domain.usecase

import com.albuquerque.domain.model.Movie
import com.albuquerque.domain.model.MovieReminderDay
import com.albuquerque.domain.model.MovieReminderDay.D0
import com.albuquerque.domain.model.MovieReminderDay.D_MINUS_2
import com.albuquerque.domain.model.MovieReminderDay.D_MINUS_6
import com.albuquerque.domain.repository.MoviesRepository
import java.text.SimpleDateFormat
import java.util.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.zip

class CreateMovieReminderUseCase(
    private val repository: MoviesRepository
) {

    operator fun invoke(movie: Movie): Flow<Unit> {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale("pt", "BR"))
        try {
            val movieReleaseDate = sdf.parse(movie.releaseDate)

            if(movieReleaseDate != null) {
                val now = Calendar.getInstance().timeInMillis

                val dMinus6Delay = getReminderDateInMillis(
                    fromDate = movieReleaseDate,
                    targetDay = D_MINUS_6
                ).getTimeInMillisDiff(now)

                val dMinus2Delay = getReminderDateInMillis(
                    fromDate = movieReleaseDate,
                    targetDay = D_MINUS_2
                ).getTimeInMillisDiff(now)

                val dZeroDelay = getReminderDateInMillis(
                    fromDate = movieReleaseDate,
                    targetDay = D0
                ).getTimeInMillisDiff(now)

                val dMinus6Flow = createMovieReminder(dMinus6Delay, D_MINUS_6.day, movie)
                val dMinus2Flow = createMovieReminder(dMinus2Delay, D_MINUS_2.day, movie)
                val dZeroFlow = createMovieReminder(dZeroDelay, D0.day, movie)

                return dMinus6Flow
                    .zip(dMinus2Flow) { _, _ -> }
                    .zip(dZeroFlow) { _ , _ -> }

            } else {
                throw Exception("Erro ao fazer parse da data de estreia do filme")
            }

        } catch (exception: Exception) {
            throw exception
        }
    }

    private fun createMovieReminder(delay: Long, reminderDay: Int, movie: Movie): Flow<Unit> {
        if (delay <= 0) return flowOf(Unit)

        return repository.createMovieReminder(
            delayReminder = delay,
            reminderDay = reminderDay,
            movie = movie
        )
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

    private fun Long.getTimeInMillisDiff(timeInMillis: Long): Long {
        return (this@getTimeInMillisDiff / 1000L) - (timeInMillis / 1000L)
    }
}
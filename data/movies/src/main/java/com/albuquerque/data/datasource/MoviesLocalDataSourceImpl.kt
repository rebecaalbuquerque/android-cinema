package com.albuquerque.data.datasource

import com.albuquerque.common.deeplink.CinemaDeeplink
import com.albuquerque.common.remindermanager.CinemaReminderManager
import com.albuquerque.data.local.MovieDao
import com.albuquerque.data.mapper.toEntity
import com.albuquerque.data.mapper.toMovie
import com.albuquerque.domain.model.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class MoviesLocalDataSourceImpl(
    private val dao: MovieDao,
    private val cinemaReminderManager: CinemaReminderManager
) : MoviesLocalDataSource {

    override suspend fun updateFavorite(movie: Movie) {
        dao.insert(movie.toEntity())
    }

    override fun getFavorites(): Flow<List<Movie>> {
        return dao.getFavorites().map { list ->
            list.map { it.toMovie() }
        }
    }

    override fun getReminders(): Flow<List<Movie>> {
        return dao.getReminders().map { list ->
            list.map { it.toMovie() }
        }
    }

    override fun createMovieReminder(reminderInMillis: Long, reminderDay: Int, movie: Movie): Flow<Unit> {
        return flow {
            cinemaReminderManager.createNotification(
                reminderDay = reminderDay,
                deeplink = CinemaDeeplink.Reminders.Home.url,
                movieId = "${movie.id}_D${reminderDay}".hashCode(),
                movieName = movie.title,
                timeInMillis = reminderInMillis
            )
            emit(dao.insert(movie.toEntity()))
        }
    }

    override fun deleteMovieReminder(reminderDay: Int, movie: Movie): Flow<Unit> {
        return flow {
            cinemaReminderManager.deleteNotification(
                movieId = "${movie.id}_D${reminderDay}".hashCode()
            )
            emit(dao.insert(movie.toEntity()))
        }
    }
}
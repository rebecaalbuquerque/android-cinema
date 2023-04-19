package com.albuquerque.data.datasource

import androidx.lifecycle.asFlow
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.albuquerque.data.local.MovieDao
import com.albuquerque.data.local.work.ReminderMovieWorker
import com.albuquerque.data.mapper.toEntity
import com.albuquerque.data.mapper.toMovie
import com.albuquerque.domain.model.Movie
import java.util.concurrent.TimeUnit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.zip

class MoviesLocalDataSourceImpl(
    private val dao: MovieDao,
    private val workManager: WorkManager
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

    override fun createMovieReminder(delayReminder: Long, reminderDay: Int, movie: Movie): Flow<Unit> {
        val work = OneTimeWorkRequestBuilder<ReminderMovieWorker>()
            .setInitialDelay(delayReminder, TimeUnit.SECONDS)
            .addTag("MOVIE_REMINDER_${movie.id}")
            .setInputData(
                workDataOf(
                    "title" to "Cinema",
                    "message" to "Lembre de assistir ao filme ${movie.title}"
                )
            ).build()

        val createWork = workManager.enqueueUniqueWork(
            "MOVIE_REMINDER_${movie.id}_{D${reminderDay}}",
            ExistingWorkPolicy.REPLACE,
            work
        )

        //Transformar em flow e se o state for SUCCESS emite o flow, se não for joga um throw
        val createWorkFlow = createWork.state.asFlow().map { operation -> }

        val updateFlow = flow {
            emit(dao.insert(movie.toEntity()))
        }

        return createWorkFlow.zip(updateFlow) {_, _ -> }
    }

    override fun deleteMovieReminder(movie: Movie): Flow<Unit> {
        val cancelWork = workManager.cancelAllWorkByTag("MOVIE_REMINDER_${movie.id}")
        val cancelWorkFlow = cancelWork.state.asFlow()
        val updateFlow = flow {
            emit(dao.insert(movie.toEntity()))
        }

        return cancelWorkFlow.zip(updateFlow) { cancel, _ ->
            Unit
        }
    }
}
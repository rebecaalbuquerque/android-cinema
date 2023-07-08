package com.albuquerque.data.repository

import com.albuquerque.data.datasource.MoviesLocalDataSource
import com.albuquerque.data.datasource.MoviesRemoteDataSource
import com.albuquerque.common.extension.asDate
import com.albuquerque.common.extension.isBiggerThan
import com.albuquerque.data.mapper.toMovieList
import com.albuquerque.domain.model.Movie
import com.albuquerque.domain.model.MovieList
import com.albuquerque.domain.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map

class MoviesRepositoryImpl(
    private val remoteDataSource: MoviesRemoteDataSource,
    private val localDataSource: MoviesLocalDataSource
) : MoviesRepository {

    override fun getUpcomingMovies(currentTimeInMillis: Long): Flow<MovieList> {
        val upcomingMovies = remoteDataSource.getUpcomingMovies().map { it.toMovieList() }.map { movieList ->
            movieList.copy(
                results = movieList.results.map { movie ->
                    movie.copy(
                        canCreateReminder = movie.releaseDate.asDate().isBiggerThan(currentTimeInMillis)
                    )
                }
            )
        }
        val favoritesDb = localDataSource.getFavorites()
        val remindersDb = localDataSource.getReminders()

        return upcomingMovies.combine(favoritesDb) { upcoming, favorites ->
            if (favorites.isEmpty()){
                upcoming
            } else {
                upcoming.copy(
                    results = upcoming.results.map { movie ->
                        if (favorites.map { it.id }.contains(movie.id)) {
                            movie.copy(isFavorite = true)
                        } else {
                            movie
                        }
                    }
                )
            }
        }.combine(remindersDb) { upcomingMoviesUpdated, reminders ->
            upcomingMoviesUpdated.copy(
                results = upcomingMoviesUpdated.results.map { remoteMovie ->
                    if (reminders.map { it.id }.contains(remoteMovie.id)){
                        val localMovieReminderStatus = reminders
                            .firstOrNull { it.id == remoteMovie.id }
                            ?.reminderStatus
                            .orEmpty()

                        remoteMovie.copy(
                            hasReminder = true,
                            reminderStatus = localMovieReminderStatus
                        )
                    } else {
                        remoteMovie
                    }
                }
            )
        }
    }

    override suspend fun updateMovie(movie: Movie) {
        localDataSource.updateFavorite(movie)
    }

    override fun getFavorites(): Flow<List<Movie>> {
        return localDataSource.getFavorites()
    }

    override fun getReminders(): Flow<List<Movie>> {
        return localDataSource.getReminders()
    }

    override fun createMovieReminder(reminderInMillis: Long, reminderDay: Int, movie: Movie): Flow<Unit> {
        return localDataSource.createMovieReminder(reminderInMillis, reminderDay, movie)
    }

    override fun deleteMovieReminder(reminderDay: Int, movie: Movie): Flow<Unit> {
        return localDataSource.deleteMovieReminder(reminderDay, movie)
    }

    override suspend fun scheduleNotifications(
        deviceToken: String,
        movieId: Int,
        movieName: String,
        movieReleaseDate: String
    ): Result<Unit> {
        return remoteDataSource.scheduleNotification(
            movieId,
            movieName,
            movieReleaseDate,
            deviceToken,
            ""
        )
    }
}
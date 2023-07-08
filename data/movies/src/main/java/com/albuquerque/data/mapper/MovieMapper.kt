package com.albuquerque.data.mapper

import com.albuquerque.data.local.model.MovieEntity
import com.albuquerque.data.remote.model.MovieListResponse
import com.albuquerque.data.remote.model.MovieResponse
import com.albuquerque.domain.model.Movie
import com.albuquerque.domain.model.MovieList

internal fun MovieResponse.toMovie(): Movie {
    return Movie(
        id = id ?: 0,
        title = title.orEmpty(),
        overview = overview.orEmpty(),
        releaseDate = releaseDate.orEmpty(),
        isFavorite = false,
        hasReminder = false
    )
}

internal fun MovieListResponse.toMovieList(): MovieList {
    return MovieList(
        page = page ?: 0,
        results = results.orEmpty().map { it.toMovie() }
    )
}

internal fun Movie.toEntity(): MovieEntity {
    return MovieEntity(
        id = id,
        title = title,
        overview = overview,
        releaseDate = releaseDate,
        isFavorite = isFavorite,
        hasReminder = hasReminder,
        reminderStatus = Movie.ReminderStatus.getByValue(this.reminderStatus).name
    )
}

internal fun MovieEntity.toMovie(): Movie {
    return Movie(
        id = id,
        title = title.orEmpty(),
        overview = overview.orEmpty(),
        releaseDate = releaseDate.orEmpty(),
        isFavorite = isFavorite ?: false,
        hasReminder = hasReminder ?: false,
        reminderStatus = MovieEntity.ReminderStatus.getByValue(this.reminderStatus).name
    )
}
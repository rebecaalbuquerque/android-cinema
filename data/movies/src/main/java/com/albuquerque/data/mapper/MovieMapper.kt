package com.albuquerque.data.mapper

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
        isFavorite = false
    )
}

internal fun MovieListResponse.toMovieList(): MovieList {
    return MovieList(
        page = page ?: 0,
        results = results.orEmpty().map { it.toMovie() }
    )
}
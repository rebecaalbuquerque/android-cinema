package com.albuquerque.moviesupcoming.domain

import com.albuquerque.domain.model.Movie

internal class UpdateUpcomingMoviesFavoriteUseCase {

    operator fun invoke(oldMovies: List<Movie>, movie: Movie): List<Movie> {
        val oldMoviePosition = oldMovies.indexOfFirst { it.id == movie.id }

        if (oldMoviePosition < 0) return oldMovies

        val newMovies = oldMovies.toMutableList()
        newMovies[oldMoviePosition] = movie

        return newMovies
    }
}
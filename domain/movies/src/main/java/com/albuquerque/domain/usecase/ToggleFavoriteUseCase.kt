package com.albuquerque.domain.usecase

import com.albuquerque.domain.model.Movie
import com.albuquerque.domain.repository.MoviesRepository

class ToggleFavoriteUseCase(
    private val repository: MoviesRepository
) {

    suspend operator fun invoke( movie: Movie): Movie {
        val movieUpdated = movie.copy(
            isFavorite = !movie.isFavorite
        )

        repository.updateMovie(movieUpdated)

        return movieUpdated
    }
}
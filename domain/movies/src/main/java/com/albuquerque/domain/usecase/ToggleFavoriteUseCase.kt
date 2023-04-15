package com.albuquerque.domain.usecase

import com.albuquerque.domain.repository.MoviesRepository

class ToggleFavoriteUseCase(
    private val repository: MoviesRepository
) {

    operator fun invoke(movieId: Int, isFavorite: Boolean) {

    }
}
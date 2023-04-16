package com.albuquerque.favorites.domain.usecase

import com.albuquerque.domain.model.Movie
import com.albuquerque.domain.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow

internal class GetFavoriteMoviesUseCases(
    private val repository: MoviesRepository
) {

    operator fun invoke(): Flow<List<Movie>> {
        return repository.getFavorites()
    }
}
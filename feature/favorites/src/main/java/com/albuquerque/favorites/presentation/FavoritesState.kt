package com.albuquerque.favorites.presentation

import com.albuquerque.domain.model.Movie

internal sealed class FavoritesState {
    object Loading : FavoritesState()
    data class Success(val data: List<Movie>) : FavoritesState()
    data class Error(val message: String? = null) : FavoritesState()
}
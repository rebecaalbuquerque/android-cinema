package com.albuquerque.favorites.presentation

internal sealed class FavoritesAction {
    data class NavigateToMovieDetail(val movieId: Int) : FavoritesAction()
}

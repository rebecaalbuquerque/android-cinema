package com.albuquerque.moviesupcoming.presentation

internal sealed class MoviesUpcomingAction {
    data class NavigateToMovieDetail(val movieId: Int) : MoviesUpcomingAction()
}
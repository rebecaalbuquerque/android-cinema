package com.albuquerque.moviesupcoming.presentation

import com.albuquerque.domain.model.Movie

internal sealed class MoviesUpcomingState {
    object Loading : MoviesUpcomingState()
    data class Success(val data: List<Movie>) : MoviesUpcomingState()
    data class Error(val message: String? = null) : MoviesUpcomingState()
}
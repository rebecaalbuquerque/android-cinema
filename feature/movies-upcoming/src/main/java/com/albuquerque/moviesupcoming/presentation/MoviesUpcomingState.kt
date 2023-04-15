package com.albuquerque.moviesupcoming.presentation

internal sealed class MoviesUpcomingState {
    object Loading : MoviesUpcomingState()
    data class Success(val data: String) : MoviesUpcomingState()
    data class Error(val message: String? = null) : MoviesUpcomingState()
}
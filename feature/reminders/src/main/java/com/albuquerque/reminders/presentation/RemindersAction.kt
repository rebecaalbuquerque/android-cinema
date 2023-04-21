package com.albuquerque.reminders.presentation

internal sealed class RemindersAction {
    data class NavigateToMovieDetail(val movieId: Int) : RemindersAction()
}
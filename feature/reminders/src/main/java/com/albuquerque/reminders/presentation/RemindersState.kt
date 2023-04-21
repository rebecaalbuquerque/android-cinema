package com.albuquerque.reminders.presentation

import com.albuquerque.domain.model.Movie

internal sealed class RemindersState {
    object Loading : RemindersState()
    object Empty : RemindersState()
    data class Success(val data: List<Movie>) : RemindersState()
    data class Error(val message: String? = null) : RemindersState()
}
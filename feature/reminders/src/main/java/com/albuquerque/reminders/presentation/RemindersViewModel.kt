package com.albuquerque.reminders.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.albuquerque.domain.model.Movie
import com.albuquerque.domain.usecase.ToggleReminderUseCase
import com.albuquerque.reminders.domain.usecase.GetRemindersUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class RemindersViewModel(
    private val getRemindersUseCase: GetRemindersUseCase,
    private val toggleReminderUseCase: ToggleReminderUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private val _state: MutableStateFlow<RemindersState> = MutableStateFlow(
        RemindersState.Loading
    )
    val state: StateFlow<RemindersState> = _state

    private val _action: MutableStateFlow<RemindersAction?> = MutableStateFlow(null)
    val action: StateFlow<RemindersAction?> = _action

    init {
        getReminders()
    }

    fun onItemClick(movieId: Int) {
        _action.update { RemindersAction.NavigateToMovieDetail(movieId) }
    }

    fun onReminderClick(movie: Movie) {
        viewModelScope.launch(dispatcher) {
            toggleReminderUseCase(movie)
                .onStart { }
                .catch { }
                .collect { }
        }
    }

    private fun getReminders() = viewModelScope.launch {
        getRemindersUseCase()
            .flowOn(dispatcher)
            .catch { _state.update { RemindersState.Error() } }
            .collect { reminders ->
                if (reminders.isEmpty()) {
                    _state.update { RemindersState.Empty }
                } else {
                    _state.update { RemindersState.Success(reminders) }
                }
            }
    }
}
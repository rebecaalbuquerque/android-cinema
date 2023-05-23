package com.albuquerque.moviesupcoming.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.albuquerque.analytics.EventName
import com.albuquerque.analytics.EventTracker
import com.albuquerque.analytics.PropertiesName
import com.albuquerque.domain.model.Movie
import com.albuquerque.domain.usecase.ToggleFavoriteUseCase
import com.albuquerque.domain.usecase.ToggleReminderUseCase
import com.albuquerque.moviesupcoming.domain.usecase.GetUpcomingMoviesUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class MoviesUpcomingViewModel(
    private val getUpcomingMoviesUseCase: GetUpcomingMoviesUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase,
    private val toggleReminderUseCase: ToggleReminderUseCase,
    private val tracker: EventTracker,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private val _state: MutableStateFlow<MoviesUpcomingState> = MutableStateFlow(
        MoviesUpcomingState.Loading
    )
    val state: StateFlow<MoviesUpcomingState> = _state

    private val _action: MutableStateFlow<MoviesUpcomingAction?> = MutableStateFlow(null)
    val action: StateFlow<MoviesUpcomingAction?> = _action

    init {
        getMoviesUpcoming()
    }

    fun onItemClick(movieId: Int) {
        _action.update { MoviesUpcomingAction.NavigateToMovieDetail(movieId) }
    }

    fun onFavoriteClick(movie: Movie) {
        viewModelScope.launch(dispatcher) {
            toggleFavoriteUseCase(movie)
        }
    }

    fun onReminderClick(movie: Movie) {
        tracker.track(EventName.Button.Clicked.value) {
            properties {
                PropertiesName.Button.Name.value withValue "upcoming_list_reminder_button"
                PropertiesName.Button.Screen.value withValue "upcoming_list_screen"
                PropertiesName.Button.Status.value withValue movie.title
            }
        }

        viewModelScope.launch {
            toggleReminderUseCase(movie)
                .onStart { }
                .catch { }
                .collect { }
        }
    }

    private fun getMoviesUpcoming() {
        viewModelScope.launch {
            getUpcomingMoviesUseCase()
                .flowOn(dispatcher)
                .catch {
                    _state.update { MoviesUpcomingState.Error() }
                }
                .collect { movieList ->
                    if (movieList.results.isEmpty()) {
                        _state.update { MoviesUpcomingState.Empty }
                    } else {
                        _state.update { MoviesUpcomingState.Success(movieList.results) }
                    }
                }
        }
    }
}
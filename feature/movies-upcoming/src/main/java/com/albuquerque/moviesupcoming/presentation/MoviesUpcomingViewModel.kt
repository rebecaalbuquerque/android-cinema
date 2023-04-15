package com.albuquerque.moviesupcoming.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.albuquerque.domain.usecase.GetUpcomingMoviesUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class MoviesUpcomingViewModel(
    private val getUpcomingMoviesUseCase: GetUpcomingMoviesUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    init {
        getMoviesUpcoming()
    }

    private val _state: MutableStateFlow<MoviesUpcomingState> = MutableStateFlow(
        MoviesUpcomingState.Loading
    )
    val state: StateFlow<MoviesUpcomingState> = _state

    private fun getMoviesUpcoming() {
        viewModelScope.launch {
            getUpcomingMoviesUseCase()
                .flowOn(dispatcher)
                .catch {
                    _state.update { MoviesUpcomingState.Error() }
                }
                .collect { movieList ->
                    _state.update { MoviesUpcomingState.Success(movieList.toString()) }
                }
        }
    }
}
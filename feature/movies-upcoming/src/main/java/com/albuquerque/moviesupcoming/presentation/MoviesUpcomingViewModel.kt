package com.albuquerque.moviesupcoming.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.albuquerque.domain.model.Movie
import com.albuquerque.moviesupcoming.domain.usecase.GetUpcomingMoviesUseCase
import com.albuquerque.domain.usecase.ToggleFavoriteUseCase
import com.albuquerque.moviesupcoming.domain.usecase.UpdateUpcomingMoviesFavoriteUseCase
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
    private val updateUpcomingMoviesFavoriteUseCase: UpdateUpcomingMoviesFavoriteUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase,
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

    fun onFavoriteClick(movies: List<Movie>, movie: Movie) {
        viewModelScope.launch {
            val movieUpdated = toggleFavoriteUseCase(movie)
            _state.update {
                MoviesUpcomingState.Success(updateUpcomingMoviesFavoriteUseCase(movies, movieUpdated))
            }
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
                    _state.update { MoviesUpcomingState.Success(movieList.results) }
                }
        }
    }
}
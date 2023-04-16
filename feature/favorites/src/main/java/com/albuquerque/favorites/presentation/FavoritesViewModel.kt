package com.albuquerque.favorites.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.albuquerque.domain.model.Movie
import com.albuquerque.domain.usecase.ToggleFavoriteUseCase
import com.albuquerque.favorites.domain.usecase.GetFavoriteMoviesUseCases
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class FavoritesViewModel(
    private val getFavoriteMoviesUseCases: GetFavoriteMoviesUseCases,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private val _state: MutableStateFlow<FavoritesState> = MutableStateFlow(
        FavoritesState.Loading
    )
    val state: StateFlow<FavoritesState> = _state

    private val _action: MutableStateFlow<FavoritesAction?> = MutableStateFlow(null)
    val action: StateFlow<FavoritesAction?> = _action

    init {
        getFavorites()
    }

    fun onItemClick(movieId: Int) {
        _action.update { FavoritesAction.NavigateToMovieDetail(movieId) }
    }

    fun onFavoriteClick(movie: Movie) {
        viewModelScope.launch {
            toggleFavoriteUseCase(movie)
        }
    }

    private fun getFavorites() = viewModelScope.launch {
        getFavoriteMoviesUseCases()
            .flowOn(dispatcher)
            .catch {
                _state.update { FavoritesState.Error() }
            }
            .collect { favorites ->
                _state.update { FavoritesState.Success(favorites) }
            }
    }
}
package com.albuquerque.moviesupcoming.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.albuquerque.designsystem.extension.toastShort
import com.albuquerque.moviesupcoming.databinding.FragmentMoviesUpcomingBinding
import com.albuquerque.moviesupcoming.presentation.adapter.MoviesUpcomingAdapter
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class MoviesUpcomingFragment : Fragment() {

    private var binding: FragmentMoviesUpcomingBinding? = null
    private var adapter: MoviesUpcomingAdapter? = null
    private val viewModel: MoviesUpcomingViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentMoviesUpcomingBinding.inflate(inflater, container, false)
        adapter = MoviesUpcomingAdapter().apply {
            onMovieClick = { viewModel.onItemClick(it.id) }
            onFavoriteClick = { movie, isFavorite ->
                toastShort("Favorite ${movie.id}")
                viewModel.onFavoriteClick(movie.id, isFavorite)
            }
        }
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupState()
        setupAction()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
        adapter = null
    }

    private fun setupState() = lifecycleScope.launch {
        viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
            viewModel.state.collect { state ->
                when (state) {
                    MoviesUpcomingState.Loading -> {
                        binding?.containerLoading?.isVisible = true
                    }
                    is MoviesUpcomingState.Error -> {
                        binding?.containerLoading?.isVisible = false
                    }
                    is MoviesUpcomingState.Success -> {
                        binding?.containerLoading?.isVisible = false
                        binding?.recyclerViewMovies?.adapter = adapter
                        adapter?.movies = state.data
                    }
                }
            }
        }
    }

    private fun setupAction() = lifecycleScope.launch {
        viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
            viewModel.action.collect { action ->
                when (action) {
                    is MoviesUpcomingAction.NavigateToMovieDetail -> {
                        toastShort("MovieId: ${action.movieId}")
                    }
                    null -> Unit
                }
            }
        }
    }
}
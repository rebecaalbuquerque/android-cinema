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
import com.albuquerque.designsystem.extension.bindSafely
import com.albuquerque.designsystem.extension.toastShort
import com.albuquerque.moviesupcoming.databinding.FragmentMoviesUpcomingBinding
import com.albuquerque.moviesupcoming.presentation.adapter.MoviesUpcomingAdapter
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class MoviesUpcomingFragment : Fragment() {

    internal var binding: FragmentMoviesUpcomingBinding? = null
    private var adapter: MoviesUpcomingAdapter? = null
    private val viewModel: MoviesUpcomingViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentMoviesUpcomingBinding.inflate(inflater, container, false)
        adapter = MoviesUpcomingAdapter().apply {
            onMovieClick = { movie ->
                viewModel.onItemClick(movie.id)
            }
            onFavoriteClick = { movie ->
                viewModel.onFavoriteClick(this.movies, movie)
            }
            onReminderClick = { movie ->
                viewModel.onReminderClick(movie)
            }
        }
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        setupState()
        setupAction()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
        adapter = null
    }

    private fun setupView() = binding.bindSafely {
        recyclerViewMovies.adapter = adapter
    }

    private fun setupState() = lifecycleScope.launch {
        viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.state.collect { state ->
                binding.bindSafely {
                    when (state) {
                        MoviesUpcomingState.Loading -> {
                            containerLoading.isVisible = true
                            containerFeedback.isVisible = false
                        }
                        is MoviesUpcomingState.Empty -> {
                            containerLoading.isVisible = false
                            adapter?.movies = emptyList()
                            showEmptyView()
                        }
                        is MoviesUpcomingState.Error -> {
                            containerLoading.isVisible = false
                            showErrorView()
                        }
                        is MoviesUpcomingState.Success -> {
                            containerLoading.isVisible = false
                            containerFeedback.isVisible = false
                            adapter?.movies = state.data
                        }
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
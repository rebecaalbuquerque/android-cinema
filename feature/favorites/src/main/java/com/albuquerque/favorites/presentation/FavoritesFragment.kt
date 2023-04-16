package com.albuquerque.favorites.presentation

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
import com.albuquerque.favorites.databinding.FragmentFavoritesBinding
import com.albuquerque.favorites.presentation.adapter.FavoritesAdapter
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class FavoritesFragment : Fragment() {

    internal var binding: FragmentFavoritesBinding? = null
    private var adapter: FavoritesAdapter? = null
    private val viewModel: FavoritesViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        adapter = FavoritesAdapter().apply {
            onMovieClick = {
                viewModel.onItemClick(it.id)
            }
            onFavoriteClick = {
                viewModel.onFavoriteClick(it)
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
        recyclerViewFavorites.adapter = adapter
    }

    private fun setupState() = lifecycleScope.launch {
        viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.state.collect { state ->
                binding.bindSafely {
                    when (state) {
                        FavoritesState.Loading -> {
                            containerLoading.isVisible = true
                            containerFeedback.isVisible = false
                        }
                        FavoritesState.Empty -> {
                            containerLoading.isVisible = false
                            adapter?.movies = emptyList()
                            showEmptyView()
                        }
                        is FavoritesState.Error -> {
                            containerLoading.isVisible = false
                            showErrorView()
                        }
                        is FavoritesState.Success -> {
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
                    is FavoritesAction.NavigateToMovieDetail -> {
                        toastShort("MovieId: ${action.movieId}")
                    }
                    null -> Unit
                }
            }
        }
    }
}
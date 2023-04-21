package com.albuquerque.reminders.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.albuquerque.common_ui.presentation.adapter.GenericMoviesAdapter
import com.albuquerque.designsystem.extension.bindSafely
import com.albuquerque.designsystem.extension.toastShort
import com.albuquerque.reminders.databinding.FragmentRemindersBinding
import com.albuquerque.reminders.databinding.MovieReminderViewHolderBinding
import com.albuquerque.reminders.presentation.viewholder.ReminderViewHolder
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class RemindersFragment : Fragment() {

    internal var binding: FragmentRemindersBinding? = null
    private var adapter: GenericMoviesAdapter<ReminderViewHolder, MovieReminderViewHolderBinding>? = null
    private val viewModel: RemindersViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentRemindersBinding.inflate(inflater, container, false)
        adapter = GenericMoviesAdapter(
            inflate = { layoutInflater, parent, attach ->
                MovieReminderViewHolderBinding.inflate(layoutInflater, parent, attach)
            },
            viewHolder = { binding, onMovieClick, _, onReminderClick ->
                ReminderViewHolder(binding, onMovieClick, onReminderClick)
            },
            onBinding = { holder, movie ->
                holder.bind(movie)
            },
            onBindingUpdate = { holder, bundle ->
                holder.bindUpdate(bundle)
            },
            onMovieClick = { viewModel.onItemClick(it.id) },
            onReminderClick = { viewModel.onReminderClick(it) }
        )
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
        recyclerViewReminders.adapter = adapter
    }

    private fun setupState() = lifecycleScope.launch {
        viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.state.collect { state ->
                binding.bindSafely {
                    when (state) {
                        RemindersState.Loading -> {
                            containerLoading.isVisible = true
                            containerFeedback.isVisible = false
                        }
                        RemindersState.Empty -> {
                            containerLoading.isVisible = false
                            adapter?.movies = emptyList()
                            showEmptyView()
                        }
                        is RemindersState.Error -> {
                            containerLoading.isVisible = false
                            showErrorView()
                        }
                        is RemindersState.Success -> {
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
                    is RemindersAction.NavigateToMovieDetail -> {
                        toastShort("MovieId: ${action.movieId}")
                    }
                    null -> Unit
                }
            }
        }
    }
}
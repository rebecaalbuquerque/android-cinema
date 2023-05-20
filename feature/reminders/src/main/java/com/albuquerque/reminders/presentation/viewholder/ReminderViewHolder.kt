package com.albuquerque.reminders.presentation.viewholder

import android.os.Bundle
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.albuquerque.common.extension.formatDate
import com.albuquerque.common_ui.presentation.adapter.MoviesDiffUtil
import com.albuquerque.designsystem.R
import com.albuquerque.domain.model.Movie
import com.albuquerque.reminders.databinding.MovieReminderViewHolderBinding

class ReminderViewHolder(
    private val binding: MovieReminderViewHolderBinding,
    private val onMovieClick: ((Movie) -> Unit)? = null,
    private val onReminderClick: ((Movie) -> Unit)? = null
) : RecyclerView.ViewHolder(binding.root) {

    private var currentMovie: Movie? = null

    init {
        binding.root.setOnClickListener {
            currentMovie?.let {
                onMovieClick?.invoke(it)
            }
        }
        binding.reminder.setOnClickListener {
            currentMovie?.let {
                onReminderClick?.invoke(it)
            }
        }
    }

    fun bind(
        movie: Movie
    ) = with(binding) {
        currentMovie = movie

        setupButtons()

        reminder.isVisible = movie.canCreateReminder
        title.text = movie.title
        overview.text = movie.overview
        releaseDate.text = "Release date ${movie.releaseDate.formatDate()}"
    }

    fun bindUpdate(bundle: Bundle) = with(binding) {
        currentMovie = currentMovie?.copy(
            isFavorite = bundle.getBoolean(MoviesDiffUtil.ARG_IS_FAVORITE),
            hasReminder = bundle.getBoolean(MoviesDiffUtil.ARG_HAS_REMINDER)
        )
        setupButtons()
    }

    private fun setupButtons() = with(binding) {
        reminder.setImageResource(
            if (currentMovie?.hasReminder == true) R.drawable.ds_ic_reminder else R.drawable.ds_ic_reminder_outline
        )
    }
}
package com.albuquerque.moviesupcoming.presentation.viewholder

import android.os.Bundle
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.albuquerque.common.extension.formatDate
import com.albuquerque.common_ui.presentation.adapter.MoviesDiffUtil
import com.albuquerque.designsystem.R
import com.albuquerque.domain.model.Movie
import com.albuquerque.moviesupcoming.databinding.MovieUpcomingViewHolderBinding

class MoviesUpcomingViewHolder(
    private val binding: MovieUpcomingViewHolderBinding,
    private val onMovieClick: ((Movie) -> Unit)? = null,
    private val onFavoriteClick: ((Movie) -> Unit)? = null,
    private val onReminderClick: ((Movie) -> Unit)? = null
) : RecyclerView.ViewHolder(binding.root) {

    private var currentMovie: Movie? = null

    init {
        binding.root.setOnClickListener {
            currentMovie?.let {
                onMovieClick?.invoke(it)
            }
        }
        binding.favorite.setOnClickListener {
            currentMovie?.let {
                onFavoriteClick?.invoke(it)
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
            hasReminder = bundle.getBoolean(MoviesDiffUtil.ARG_HAS_REMINDER),
            reminderStatus = bundle.getString(MoviesDiffUtil.ARG_REMINDER_STATUS, Movie.ReminderStatus.NOT_SCHEDULED.name)
        )
        setupButtons()
    }

    private fun setupButtons() = with(binding) {
        favorite.setImageResource(
            if (currentMovie?.isFavorite == true) R.drawable.ds_ic_favorite else R.drawable.ds_ic_favorite_outline
        )

        when (Movie.ReminderStatus.getByValue(currentMovie?.reminderStatus)) {
            Movie.ReminderStatus.TRYING_TO_SCHEDULE -> {
                reminder.isInvisible = true
                reminderLoading.isVisible = true
            }
            Movie.ReminderStatus.SCHEDULED -> {
                reminder.setImageResource(R.drawable.ds_ic_reminder)
                reminder.isVisible = true
                reminderLoading.isInvisible = true
            }
            Movie.ReminderStatus.NOT_SCHEDULED -> {
                reminder.setImageResource(R.drawable.ds_ic_reminder_outline)
                reminder.isVisible = true
                reminderLoading.isInvisible = true
            }
        }
    }
}
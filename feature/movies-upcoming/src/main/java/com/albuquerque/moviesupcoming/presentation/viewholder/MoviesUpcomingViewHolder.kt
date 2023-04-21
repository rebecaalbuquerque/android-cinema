package com.albuquerque.moviesupcoming.presentation.viewholder

import android.os.Bundle
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.albuquerque.common.extension.toBrazilianDate
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
        releaseDate.text = "Release date ${movie.releaseDate.toBrazilianDate()}"
    }

    fun bindUpdate(bundle: Bundle) = with(binding) {
        currentMovie = currentMovie?.copy(
            isFavorite = bundle.getBoolean(MoviesDiffUtil.ARG_IS_FAVORITE),
            hasReminder = bundle.getBoolean(MoviesDiffUtil.ARG_HAS_REMINDER)
        )
        setupButtons()
    }

    private fun setupButtons() = with(binding) {
        favorite.setImageResource(
            if (currentMovie?.isFavorite == true) R.drawable.ds_ic_favorite else R.drawable.ds_ic_favorite_outline
        )
        reminder.setImageResource(
            if (currentMovie?.hasReminder == true) R.drawable.ds_ic_reminder else R.drawable.ds_ic_reminder_outline
        )
    }
}
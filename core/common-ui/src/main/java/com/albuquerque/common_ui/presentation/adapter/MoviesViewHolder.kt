package com.albuquerque.common_ui.presentation.adapter

import android.os.Bundle
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.albuquerque.common_ui.databinding.CommonUiMovieViewHolderBinding
import com.albuquerque.common_ui.extension.onClickListener
import com.albuquerque.designsystem.R
import com.albuquerque.domain.model.Movie

@Deprecated("Use GenericMoviesAdapter instead.")
class MoviesViewHolder(
    private val binding: CommonUiMovieViewHolderBinding,
    private val onMovieClick: ((Movie) -> Unit),
    private val onFavoriteClick: ((Movie) -> Unit),
    private val onReminderClick: ((Movie) -> Unit)
) : RecyclerView.ViewHolder(binding.root) {

    private var currentMovie: Movie? = null

    init {
        binding.root.onClickListener {
            currentMovie?.let {
                onMovieClick(it)
            }
        }
        binding.favorite.onClickListener {
            currentMovie?.let {
                onFavoriteClick(it)
            }
        }
        binding.reminder.onClickListener {
            currentMovie?.let {
                onReminderClick(it)
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
        releaseDate.text = movie.releaseDate
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
package com.albuquerque.favorites.presentation.viewholder

import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.albuquerque.common_ui.presentation.adapter.MoviesDiffUtil
import com.albuquerque.designsystem.R
import com.albuquerque.domain.model.Movie
import com.albuquerque.favorites.databinding.MovieFavoriteViewHolderBinding

internal class FavoritesViewHolder(
    private val binding: MovieFavoriteViewHolderBinding,
    private val onMovieClick: ((Movie) -> Unit)? = null,
    private val onFavoriteClick: ((Movie) -> Unit)? = null

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
    }

    fun bind(
        movie: Movie
    ) = with(binding) {
        currentMovie = movie

        setupButtons()

        title.text = movie.title
        overview.text = movie.overview
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
    }
}
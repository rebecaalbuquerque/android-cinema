package com.albuquerque.favorites.presentation.adapter

import androidx.recyclerview.widget.RecyclerView
import com.albuquerque.designsystem.extension.onClickListener
import com.albuquerque.domain.model.Movie
import com.albuquerque.designsystem.R
import com.albuquerque.favorites.databinding.MovieFavoriteViewHolderBinding

internal class FavoritesViewHolder(
    private val binding: MovieFavoriteViewHolderBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(
        movie: Movie,
        onMovieClick: ((Movie) -> Unit)?,
        onFavoriteClick: ((Movie) -> Unit)?
    ) = with(binding) {
        root.onClickListener { onMovieClick?.invoke(movie) }
        favorite.onClickListener { onFavoriteClick?.invoke(movie) }
        favorite.setImageResource(
            if (movie.isFavorite) R.drawable.ds_ic_favorite else R.drawable.ds_ic_favorite_outline
        )
        title.text = movie.title
        overview.text = movie.overview
    }
}
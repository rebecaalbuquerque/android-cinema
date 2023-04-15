package com.albuquerque.moviesupcoming.presentation.adapter

import androidx.recyclerview.widget.RecyclerView
import com.albuquerque.designsystem.extension.onClickListener
import com.albuquerque.domain.model.Movie
import com.albuquerque.designsystem.R
import com.albuquerque.moviesupcoming.databinding.MovieUpcomingViewHolderBinding

internal class MoviesUpcomingViewHolder(
    private val binding: MovieUpcomingViewHolderBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(
        movie: Movie,
        onMovieClick: ((Movie) -> Unit)?,
        onFavoriteClick: ((Movie, Boolean) -> Unit)?
    ) = with(binding) {
        root.onClickListener { onMovieClick?.invoke(movie) }
        favorite.onClickListener { onFavoriteClick?.invoke(movie, !movie.isFavorite) }
        favorite.setImageResource(
            if (movie.isFavorite) R.drawable.ds_ic_favorite else R.drawable.ds_ic_favorite_outline
        )
        title.text = movie.title
        overview.text = movie.overview
    }
}
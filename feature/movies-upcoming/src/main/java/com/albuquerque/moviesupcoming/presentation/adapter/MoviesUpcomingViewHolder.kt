package com.albuquerque.moviesupcoming.presentation.adapter

import androidx.recyclerview.widget.RecyclerView
import com.albuquerque.designsystem.extension.onClickListener
import com.albuquerque.domain.model.Movie
import com.albuquerque.moviesupcoming.databinding.MovieUpcomingViewHolderBinding

internal class MoviesUpcomingViewHolder(
    private val binding: MovieUpcomingViewHolderBinding
) : RecyclerView.ViewHolder(binding.root) {

   fun bind(movie: Movie, onMovieClick: ((Movie) -> Unit)?) = with(binding) {
       root.onClickListener { onMovieClick?.invoke(movie) }
       title.text = movie.title
       overview.text = movie.overview
   }
}
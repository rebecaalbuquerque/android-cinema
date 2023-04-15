package com.albuquerque.moviesupcoming.presentation.adapter

import androidx.recyclerview.widget.RecyclerView
import com.albuquerque.domain.model.Movie
import com.albuquerque.moviesupcoming.databinding.MovieUpcomingViewHolderBinding

internal class MoviesUpcomingViewHolder(
    private val binding: MovieUpcomingViewHolderBinding
) : RecyclerView.ViewHolder(binding.root) {

   fun bind(movie: Movie) = with(binding) {
       title.text = movie.title
       overview.text = movie.overview
   }
}
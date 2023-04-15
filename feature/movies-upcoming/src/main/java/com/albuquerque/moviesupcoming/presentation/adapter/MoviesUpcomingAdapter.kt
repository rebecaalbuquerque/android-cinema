package com.albuquerque.moviesupcoming.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.albuquerque.domain.model.Movie
import com.albuquerque.moviesupcoming.databinding.MovieUpcomingViewHolderBinding

internal class MoviesUpcomingAdapter : RecyclerView.Adapter<MoviesUpcomingViewHolder>() {

    var movies = emptyList<Movie>()
        set(value) {
            val diffUtilCallback = MoviesUpcomingDiffUtil(field, value)
            val diffUtilResult = DiffUtil.calculateDiff(diffUtilCallback)
            field = value
            diffUtilResult.dispatchUpdatesTo(this)
        }

    var onMovieClick: ((Movie) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesUpcomingViewHolder {
        val view = MovieUpcomingViewHolderBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return MoviesUpcomingViewHolder(view)
    }

    override fun getItemCount(): Int =
        movies.size

    override fun onBindViewHolder(holder: MoviesUpcomingViewHolder, position: Int) {
        holder.bind(movies[position], onMovieClick)
    }


}
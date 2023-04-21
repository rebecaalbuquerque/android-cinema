package com.albuquerque.moviesupcoming.presentation.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.albuquerque.domain.model.Movie
import com.albuquerque.moviesupcoming.databinding.MovieUpcomingViewHolderBinding

internal class MoviesUpcomingAdapter(
    private var onMovieClick: ((Movie) -> Unit),
    private var onFavoriteClick: ((Movie) -> Unit),
    private var onReminderClick: ((Movie) -> Unit)
) : RecyclerView.Adapter<MoviesUpcomingViewHolder>() {

    companion object {
        const val ARG_IS_FAVORITE = "ARG_IS_FAVORITE"
        const val ARG_HAS_REMINDER = "ARG_HAS_REMINDER"
    }

    var movies = emptyList<Movie>()
        set(value) {
            val diffUtilCallback = MoviesUpcomingDiffUtil(field, value)
            val diffUtilResult = DiffUtil.calculateDiff(diffUtilCallback)
            field = value
            diffUtilResult.dispatchUpdatesTo(this)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesUpcomingViewHolder {
        val view = MovieUpcomingViewHolderBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return MoviesUpcomingViewHolder(view, onMovieClick, onFavoriteClick, onReminderClick)
    }

    override fun getItemCount(): Int =
        movies.size

    override fun onBindViewHolder(holder: MoviesUpcomingViewHolder, position: Int) {
        onBindViewHolder(holder, position, mutableListOf())
    }

    override fun onBindViewHolder(holder: MoviesUpcomingViewHolder, position: Int, payloads: MutableList<Any>) {
        val bundle = payloads.firstOrNull() as? Bundle

        if (payloads.isEmpty() || bundle == null) {
            holder.bind(movies[position])
        } else {
            holder.bindUpdate(bundle)
        }
    }

}
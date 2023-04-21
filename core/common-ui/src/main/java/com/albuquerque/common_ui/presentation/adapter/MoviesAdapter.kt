package com.albuquerque.common_ui.presentation.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.albuquerque.common_ui.databinding.CommonUiMovieViewHolderBinding
import com.albuquerque.domain.model.Movie

@Deprecated("Use GenericMoviesAdapter instead.")
class MoviesAdapter(
    private var onMovieClick: ((Movie) -> Unit),
    private var onFavoriteClick: ((Movie) -> Unit),
    private var onReminderClick: ((Movie) -> Unit)
) : RecyclerView.Adapter<MoviesViewHolder>() {

    var movies = emptyList<Movie>()
        set(value) {
            val diffUtilCallback = MoviesDiffUtil(field, value)
            val diffUtilResult = DiffUtil.calculateDiff(diffUtilCallback)
            field = value
            diffUtilResult.dispatchUpdatesTo(this)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val view = CommonUiMovieViewHolderBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )

        return MoviesViewHolder(view, onMovieClick, onFavoriteClick, onReminderClick)
    }

    override fun getItemCount(): Int =
        movies.size

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        onBindViewHolder(holder, position, mutableListOf())
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int, payloads: MutableList<Any>) {
        val bundle = payloads.firstOrNull() as? Bundle

        if (payloads.isEmpty() || bundle == null) {
            holder.bind(movies[position])
        } else {
            holder.bindUpdate(bundle)
        }
    }

}
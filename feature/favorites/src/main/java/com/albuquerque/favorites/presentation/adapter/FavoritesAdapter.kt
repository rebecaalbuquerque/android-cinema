package com.albuquerque.favorites.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.albuquerque.domain.model.Movie
import com.albuquerque.favorites.databinding.MovieFavoriteViewHolderBinding

internal class FavoritesAdapter : RecyclerView.Adapter<FavoritesViewHolder>() {

    var movies = emptyList<Movie>()
        set(value) {
            val diffUtilCallback = FavoritesDiffUtil(field, value)
            val diffUtilResult = DiffUtil.calculateDiff(diffUtilCallback)
            field = value
            diffUtilResult.dispatchUpdatesTo(this)
        }

    var onMovieClick: ((Movie) -> Unit)? = null
    var onFavoriteClick: ((Movie) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesViewHolder {
        val view = MovieFavoriteViewHolderBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return FavoritesViewHolder(view)
    }

    override fun getItemCount(): Int =
        movies.size

    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) {
        holder.bind(
            movies[position],
            onMovieClick,
            onFavoriteClick
        )
    }


}
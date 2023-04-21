package com.albuquerque.common_ui.presentation.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.albuquerque.domain.model.Movie

class GenericMoviesAdapter<VH : RecyclerView.ViewHolder, VB : ViewBinding>(
    private val inflate: (layoutInflater: LayoutInflater, parent: ViewGroup, attach: Boolean) -> VB,
    private val viewHolder: (binding: VB, onMovieClick: (Movie) -> Unit, onFavoriteClick: (Movie) -> Unit, onReminderClick: (Movie) -> Unit) -> VH,
    private val onBinding: (holder: VH, movie: Movie) -> Unit,
    private val onBindingUpdate: (holder: VH, bundle: Bundle) -> Unit,
    private var onMovieClick: ((Movie) -> Unit),
    private var onFavoriteClick: ((Movie) -> Unit),
    private var onReminderClick: ((Movie) -> Unit)
) : RecyclerView.Adapter<VH>() {

    var movies = emptyList<Movie>()
        set(value) {
            val diffUtilCallback = MoviesDiffUtil(field, value)
            val diffUtilResult = DiffUtil.calculateDiff(diffUtilCallback)
            field = value
            diffUtilResult.dispatchUpdatesTo(this)
        }

    override fun getItemCount(): Int = movies.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view = inflate.invoke(LayoutInflater.from(parent.context), parent, false)
        return viewHolder(view, onMovieClick, onFavoriteClick, onReminderClick)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        onBindViewHolder(holder, position, mutableListOf())
    }

    override fun onBindViewHolder(holder: VH, position: Int, payloads: MutableList<Any>) {
        val item = movies[position]
        val bundle = payloads.firstOrNull() as? Bundle

        if (payloads.isEmpty() || bundle == null) {
            onBinding(holder, item)
        } else {
            onBindingUpdate(holder, bundle)
        }
    }
}
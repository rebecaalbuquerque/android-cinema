package com.albuquerque.moviesupcoming.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import com.albuquerque.domain.model.Movie

internal class MoviesUpcomingDiffUtil(
    private val old: List<Movie>,
    private val new: List<Movie>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int =
        old.size

    override fun getNewListSize(): Int =
        new.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        old[oldItemPosition] == new[newItemPosition]

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        old[oldItemPosition].id == new[newItemPosition].id
}
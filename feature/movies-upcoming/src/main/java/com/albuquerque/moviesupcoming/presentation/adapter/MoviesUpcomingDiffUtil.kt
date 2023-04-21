package com.albuquerque.moviesupcoming.presentation.adapter

import android.os.Bundle
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
        old[oldItemPosition].id == new[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        old[oldItemPosition] == new[newItemPosition]

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        val oldItem = old[oldItemPosition]
        val newItem = new[newItemPosition]

        if (oldItem.id != newItem.id) return super.getChangePayload(oldItemPosition, newItemPosition)

        return if (oldItem.isFavorite == newItem.isFavorite && oldItem.hasReminder == newItem.hasReminder) {
            super.getChangePayload(oldItemPosition, newItemPosition)
        } else {
            Bundle().apply {
                putBoolean(MoviesUpcomingAdapter.ARG_IS_FAVORITE, newItem.isFavorite)
                putBoolean(MoviesUpcomingAdapter.ARG_HAS_REMINDER, newItem.hasReminder)
            }
        }

    }
}
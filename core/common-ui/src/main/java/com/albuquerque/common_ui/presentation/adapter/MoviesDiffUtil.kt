package com.albuquerque.common_ui.presentation.adapter

import android.os.Bundle
import androidx.recyclerview.widget.DiffUtil
import com.albuquerque.domain.model.Movie

class MoviesDiffUtil(
    private val old: List<Movie>,
    private val new: List<Movie>
) : DiffUtil.Callback() {

    companion object {
        const val ARG_IS_FAVORITE = "ARG_IS_FAVORITE"
        const val ARG_HAS_REMINDER = "ARG_HAS_REMINDER"
        const val ARG_REMINDER_STATUS = "ARG_REMINDER_STATUS"
    }

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
                putBoolean(ARG_IS_FAVORITE, newItem.isFavorite)
                putBoolean(ARG_HAS_REMINDER, newItem.hasReminder)
                putString(ARG_REMINDER_STATUS, newItem.reminderStatus)
            }
        }

    }
}
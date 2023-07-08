package com.albuquerque.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey
    val id: Int,

    @ColumnInfo(name = "title")
    val title: String? = null,

    @ColumnInfo(name = "overview")
    val overview: String? = null,

    @ColumnInfo(name = "releaseDate")
    val releaseDate: String? = null,

    @ColumnInfo(name = "isFavorite")
    val isFavorite: Boolean? = null,

    @ColumnInfo(name = "hasReminder")
    val hasReminder: Boolean? = null,

    @ColumnInfo(name = "reminderStatus")
    val reminderStatus: String = ReminderStatus.NOT_SCHEDULED.name
) {
    enum class ReminderStatus {
        SCHEDULED, TRYING_TO_SCHEDULE, NOT_SCHEDULED;

        companion object {
            fun getByValue(value: String): ReminderStatus {
                return values().firstOrNull { it.name == value } ?: NOT_SCHEDULED
            }
        }
    }
}

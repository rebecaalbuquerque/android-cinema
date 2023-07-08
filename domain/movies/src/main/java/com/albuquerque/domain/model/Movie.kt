package com.albuquerque.domain.model

data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val releaseDate: String,
    val isFavorite: Boolean,
    val hasReminder: Boolean,
    val canCreateReminder: Boolean = true,
    var reminderStatus: String = ReminderStatus.NOT_SCHEDULED.name
) {
    enum class ReminderStatus {
        SCHEDULED, TRYING_TO_SCHEDULE, NOT_SCHEDULED;

        companion object {
            fun getByValue(value: String?): ReminderStatus {
                return values().firstOrNull { it.name == value } ?: NOT_SCHEDULED
            }
        }
    }
}
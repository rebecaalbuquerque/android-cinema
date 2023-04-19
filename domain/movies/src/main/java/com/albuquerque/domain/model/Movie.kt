package com.albuquerque.domain.model

data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val releaseDate: String,
    val isFavorite: Boolean,
    val hasReminder: Boolean,
    val canCreateReminder: Boolean = true
)
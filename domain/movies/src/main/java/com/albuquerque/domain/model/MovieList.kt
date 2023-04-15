package com.albuquerque.domain.model

data class MovieList(
    val page: Int,
    val results: List<Movie>
)
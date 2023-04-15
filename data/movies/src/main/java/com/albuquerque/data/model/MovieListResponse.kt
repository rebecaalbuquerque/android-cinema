package com.albuquerque.data.model

import com.google.gson.annotations.SerializedName

data class MovieListResponse(
    @SerializedName("page")
    val page: Int? = null,

    @SerializedName("results")
    val results: List<MovieResponse>? = null
)
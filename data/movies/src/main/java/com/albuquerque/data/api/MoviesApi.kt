package com.albuquerque.data.api

import com.albuquerque.data.model.MovieListResponse
import retrofit2.http.GET

interface MoviesApi {

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(): MovieListResponse
}
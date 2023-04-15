package com.albuquerque.data.remote

import com.albuquerque.data.remote.model.MovieListResponse
import retrofit2.http.GET

interface MoviesApi {

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(): MovieListResponse
}
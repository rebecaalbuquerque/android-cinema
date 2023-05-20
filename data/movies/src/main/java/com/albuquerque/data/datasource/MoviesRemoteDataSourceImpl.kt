package com.albuquerque.data.datasource

import com.albuquerque.common.extension.addTime
import com.albuquerque.common.extension.asDateString
import com.albuquerque.data.BuildConfig
import com.albuquerque.data.remote.MoviesApi
import com.albuquerque.data.remote.model.MovieListResponse
import com.albuquerque.data.remote.model.MovieResponse
import java.util.Calendar
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MoviesRemoteDataSourceImpl(
    private val api: MoviesApi
) : MoviesRemoteDataSource {

    override fun getUpcomingMovies(): Flow<MovieListResponse> = flow {
        val response = api.getUpcomingMovies()

        if (!BuildConfig.DEBUG) {
            emit(response)
        } else {
            emit(
                response.copy(
                    results = response.results?.toMutableList()?.apply {
                        val testD0 = Calendar.getInstance().addTime(Calendar.DAY_OF_MONTH, 1)
                        val testD2 = Calendar.getInstance().addTime(Calendar.DAY_OF_MONTH, 2)
                        val testD6 = Calendar.getInstance().addTime(Calendar.DAY_OF_MONTH, 6)

                        add(
                            MovieResponse(
                                id = -1,
                                title = "Teste D0",
                                overview = testD0.asDateString(),
                                releaseDate = testD0.asDateString("yyyy-MM-dd")
                            )
                        )
                        add(
                            MovieResponse(
                                id = -2,
                                title = "Teste D-2",
                                overview = testD2.asDateString(),
                                releaseDate = testD2.asDateString("yyyy-MM-dd")
                            )
                        )
                        add(
                            MovieResponse(
                                id = -3,
                                title = "Teste D-6",
                                overview = testD6.asDateString(),
                                releaseDate = testD6.asDateString("yyyy-MM-dd")
                            )
                        )
                    }
                )
            )
        }
    }
}
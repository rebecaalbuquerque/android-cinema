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
import kotlin.random.Random

class MoviesRemoteDataSourceImpl(
    private val api: MoviesApi
) : MoviesRemoteDataSource {

    override fun getUpcomingMovies(): Flow<MovieListResponse> = flow {
        val response = api.getUpcomingMovies()

        if (!BuildConfig.DEBUG) {
            emit(response)
        } else {
            val fakeMovies = listOf("football","jockey","need","urgency","prayer","stress","functional","can","forward","put","debt","operational","incident","engagement","content","late","surprise","pioneer","acceptable","distributor","machinery","honest","inch","horror","preference")

            emit(
                response.copy(
                    results = response.results?.toMutableList()?.apply {
                        val movie1 = Calendar.getInstance().addTime(Calendar.DAY_OF_MONTH, 7)
                        val nameMovie1 = fakeMovies.shuffled().take((1..4).random()).joinToString(" ").replaceFirstChar { it.titlecase() }

                        val movie2 = Calendar.getInstance().addTime(Calendar.DAY_OF_MONTH, 7)
                        val nameMovie2 = fakeMovies.shuffled().take((1..4).random()).joinToString(" ").replaceFirstChar { it.titlecase() }

                        val movie3 = Calendar.getInstance().addTime(Calendar.DAY_OF_MONTH, 7)
                        val nameMovie3 = fakeMovies.shuffled().take((1..4).random()).joinToString(" ").replaceFirstChar { it.titlecase() }

                        val movie4 = Calendar.getInstance().addTime(Calendar.DAY_OF_MONTH, 5)
                        val nameMovie4 = fakeMovies.shuffled().take((1..4).random()).joinToString(" ").replaceFirstChar { it.titlecase() }

                        val movie5 = Calendar.getInstance().addTime(Calendar.DAY_OF_MONTH, 2)
                        val nameMovie5 = fakeMovies.shuffled().take((1..4).random()).joinToString(" ").replaceFirstChar { it.titlecase() }

                        add(
                            MovieResponse(
                                id = nameMovie1.hashCode(),
                                title = nameMovie1,
                                overview = movie1.asDateString(),
                                releaseDate = movie1.asDateString("yyyy-MM-dd")
                            )
                        )

                        add(
                            MovieResponse(
                                id = nameMovie2.hashCode(),
                                title = nameMovie2,
                                overview = movie2.asDateString(),
                                releaseDate = movie2.asDateString("yyyy-MM-dd")
                            )
                        )

                        add(
                            MovieResponse(
                                id = nameMovie3.hashCode(),
                                title = nameMovie3,
                                overview = movie3.asDateString(),
                                releaseDate = movie3.asDateString("yyyy-MM-dd")
                            )
                        )

                        add(
                            MovieResponse(
                                id = nameMovie4.hashCode(),
                                title = nameMovie4,
                                overview = movie4.asDateString(),
                                releaseDate = movie4.asDateString("yyyy-MM-dd")
                            )
                        )

                        add(
                            MovieResponse(
                                id = nameMovie5.hashCode(),
                                title = nameMovie5,
                                overview = movie5.asDateString(),
                                releaseDate = movie5.asDateString("yyyy-MM-dd")
                            )
                        )
                    }
                )
            )
        }
    }
}
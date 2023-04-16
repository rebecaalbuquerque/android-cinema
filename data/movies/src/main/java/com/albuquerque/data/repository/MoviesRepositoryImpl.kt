package com.albuquerque.data.repository

import com.albuquerque.data.datasource.MoviesLocalDataSource
import com.albuquerque.data.datasource.MoviesRemoteDataSource
import com.albuquerque.data.mapper.toMovieList
import com.albuquerque.domain.model.Movie
import com.albuquerque.domain.model.MovieList
import com.albuquerque.domain.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.zip

class MoviesRepositoryImpl(
    private val remoteDataSource: MoviesRemoteDataSource,
    private val localDataSource: MoviesLocalDataSource
) : MoviesRepository {

    override fun getUpcomingMovies(): Flow<MovieList> {
        val upcomingMovies = remoteDataSource.getUpcomingMovies().map { it.toMovieList() }

        return upcomingMovies.combine(localDataSource.getFavorites()) { upcoming, favorites ->
            if (favorites.isEmpty()){
                upcoming
            } else {
                upcoming.copy(
                    results = upcoming.results.map { movie ->
                        if (favorites.map { it.id }.contains(movie.id)) {
                            movie.copy(isFavorite = true)
                        } else {
                            movie
                        }
                    }
                )
            }
        }
    }

    override suspend fun updateFavorite(movie: Movie) {
        localDataSource.updateFavorite(movie)
    }

    override fun getFavorites(): Flow<List<Movie>> {
        return localDataSource.getFavorites()
    }
}
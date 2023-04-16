package com.albuquerque.data.datasource

import com.albuquerque.data.local.MovieDao
import com.albuquerque.data.mapper.toEntity
import com.albuquerque.data.mapper.toMovie
import com.albuquerque.domain.model.Movie
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class MoviesLocalDataSourceImpl(
    private val dao: MovieDao
) : MoviesLocalDataSource {

    override suspend fun updateFavorite(movie: Movie) {
        dao.updateFavorite(movie.toEntity())
    }

    override fun getFavorites(): Flow<List<Movie>> {
        CoroutineScope(Dispatchers.IO).launch {
            delay(7000)
        }
        return dao.getFavorites().map { list ->
            list.map { it.toMovie() }
        }
    }
}
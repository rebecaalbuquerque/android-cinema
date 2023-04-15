package com.albuquerque.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.albuquerque.data.local.model.MovieEntity

@Dao
interface MovieDao {

    @Insert
    fun insertFavorite(movieEntity: MovieEntity)

    @Query("SELECT * FROM movies WHERE isFavorite = 1")
    fun getFavorites(): List<MovieEntity>
}
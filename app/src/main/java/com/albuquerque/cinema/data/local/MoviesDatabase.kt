package com.albuquerque.cinema.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.albuquerque.data.local.MovieDao
import com.albuquerque.data.local.model.MovieEntity

@Database(entities = [MovieEntity::class], version = 1)
abstract class MoviesDatabase : RoomDatabase() {
    abstract fun moviesDao(): MovieDao
}
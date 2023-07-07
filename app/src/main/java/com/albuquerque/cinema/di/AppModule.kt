package com.albuquerque.cinema.di

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.albuquerque.cinema.MainAppViewModel
import com.albuquerque.cinema.data.local.MoviesDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

private val Context.dataStore by preferencesDataStore(
    name = "CINEMA_APP_PREFERENCES"
)

val appModule = module {
    single {
        Room.databaseBuilder(
            androidApplication(),
            MoviesDatabase::class.java,
            "movies-db"
        ).build()
    }
    single {
        val database = get<MoviesDatabase>()
        database.moviesDao()
    }
    single {
        androidContext().dataStore
    }
    viewModel {
        MainAppViewModel(
            saveFcmTokenUseCase = get(),
            saveDeviceUuidUseCase = get()
        )
    }
}
package com.albuquerque.data.di

import androidx.room.Room
import com.albuquerque.data.remote.MoviesApi
import com.albuquerque.data.core.interceptor.ApiInterceptor
import com.albuquerque.data.datasource.MoviesLocalDataSource
import com.albuquerque.data.datasource.MoviesLocalDataSourceImpl
import com.albuquerque.data.datasource.MoviesRemoteDataSource
import com.albuquerque.data.datasource.MoviesRemoteDataSourceImpl
import com.albuquerque.data.local.MovieDao
import com.albuquerque.data.local.MoviesDatabase
import com.albuquerque.data.repository.MoviesRepositoryImpl
import com.albuquerque.domain.repository.MoviesRepository
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    single<Gson> {
        GsonBuilder().create()
    }

    single {
        OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            )
            .addInterceptor(ApiInterceptor())
            .build()
    }

    single<GsonConverterFactory> {
        GsonConverterFactory.create(get())
    }

    single<RxJava2CallAdapterFactory> {
        RxJava2CallAdapterFactory.create()
    }

    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .client(get())
            .addConverterFactory(get<GsonConverterFactory>())
            .addCallAdapterFactory(get<RxJava2CallAdapterFactory>())
            .build()
    }
}

val databaseModule = module {

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
}

val moviesDataModule = module {
    factory { get<Retrofit>().create(MoviesApi::class.java) }

    factory<MoviesRemoteDataSource> { MoviesRemoteDataSourceImpl(api = get()) }
    factory<MoviesLocalDataSource> { MoviesLocalDataSourceImpl(dao = get()) }

    factory<MoviesRepository> {
        MoviesRepositoryImpl(remoteDataSource = get(), localDataSource = get())
    }
}
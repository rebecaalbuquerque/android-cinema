package com.albuquerque.data.di

import com.albuquerque.common.remindermanager.CinemaReminderManagerImpl
import com.albuquerque.data.core.interceptor.ApiInterceptor
import com.albuquerque.data.datasource.MoviesLocalDataSource
import com.albuquerque.data.datasource.MoviesLocalDataSourceImpl
import com.albuquerque.data.datasource.MoviesRemoteDataSource
import com.albuquerque.data.datasource.MoviesRemoteDataSourceImpl
import com.albuquerque.data.remote.MoviesApi
import com.albuquerque.data.remote.MoviesNotificationsApi
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
    }

    single<GsonConverterFactory> {
        GsonConverterFactory.create(get())
    }

    single<RxJava2CallAdapterFactory> {
        RxJava2CallAdapterFactory.create()
    }

    single {
        Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .client(
                get<OkHttpClient.Builder>()
                    .addInterceptor(ApiInterceptor())
                    .build()
            )
            .addConverterFactory(get<GsonConverterFactory>())
            .addCallAdapterFactory(get<RxJava2CallAdapterFactory>())
            .build()
            .create(MoviesApi::class.java)
    }

    single {
        Retrofit.Builder()
            .baseUrl("https://cinema-backend-app-f958bf189e10.herokuapp.com/")
            .client(
                get<OkHttpClient.Builder>().build()
            )
            .addConverterFactory(get<GsonConverterFactory>())
            .addCallAdapterFactory(get<RxJava2CallAdapterFactory>())
            .build()
            .create(MoviesNotificationsApi::class.java)
    }
}

val moviesDataModule = module {

    factory<MoviesRemoteDataSource> {
        MoviesRemoteDataSourceImpl(
            moviesApi = get(),
            moviesNotificationApi = get()
        )
    }

    factory<MoviesLocalDataSource> {
        MoviesLocalDataSourceImpl(
            dao = get(),
            cinemaReminderManager = CinemaReminderManagerImpl(androidApplication())
        )
    }

    factory<MoviesRepository> {
        MoviesRepositoryImpl(remoteDataSource = get(), localDataSource = get())
    }
}
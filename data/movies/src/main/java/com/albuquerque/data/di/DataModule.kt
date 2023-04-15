package com.albuquerque.data.di

import com.albuquerque.data.api.MoviesApi
import com.albuquerque.data.datasource.MoviesLocalDataSource
import com.albuquerque.data.datasource.MoviesLocalDataSourceImpl
import com.albuquerque.data.datasource.MoviesRemoteDataSource
import com.albuquerque.data.datasource.MoviesRemoteDataSourceImpl
import com.albuquerque.data.repository.MoviesRepositoryImpl
import com.albuquerque.domain.repository.MoviesRepository
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    single<Gson> { GsonBuilder().create() }
    single {
        OkHttpClient.Builder().build()
    }
    single<GsonConverterFactory> { GsonConverterFactory.create(get()) }
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

val moviesDataModule = module {
    factory { get<Retrofit>().create(MoviesApi::class.java) }

    factory<MoviesRemoteDataSource>{ MoviesRemoteDataSourceImpl(api = get()) }
    factory<MoviesLocalDataSource>{ MoviesLocalDataSourceImpl() }

    factory<MoviesRepository> {
        MoviesRepositoryImpl(remoteDataSource = get(), localDataSource = get())
    }
}
package com.albuquerque.auth.di

import com.albuquerque.auth.repository.AuthRepository
import com.albuquerque.auth.datasource.AuthLocalDataSource
import com.albuquerque.auth.datasource.AuthLocalDataSourceImpl
import com.albuquerque.auth.repository.AuthRepositoryImpl
import org.koin.dsl.module


val authDataModule = module {

    factory<AuthLocalDataSource> {
        AuthLocalDataSourceImpl(
            dataStore = get()
        )
    }

    factory<AuthRepository> {
        AuthRepositoryImpl(
            localDataSource = get()
        )
    }
}
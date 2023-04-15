package com.albuquerque.domain.di

import com.albuquerque.domain.usecase.GetUpcomingMoviesUseCase
import org.koin.dsl.module

val moviesDomainModule = module {
    factory { GetUpcomingMoviesUseCase(repository = get()) }
}
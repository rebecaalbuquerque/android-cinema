package com.albuquerque.domain.di

import com.albuquerque.domain.usecase.GetUpcomingMoviesUseCase
import com.albuquerque.domain.usecase.ToggleFavoriteUseCase
import org.koin.dsl.module

val moviesDomainModule = module {
    factory { GetUpcomingMoviesUseCase(repository = get()) }
    factory { ToggleFavoriteUseCase(repository = get()) }
}
package com.albuquerque.moviesupcoming.di

import com.albuquerque.moviesupcoming.navigation.MoviesUpcomingNavigationImpl
import com.albuquerque.moviesupcoming.presentation.MoviesUpcomingViewModel
import com.albuquerque.navigation.moviesupcoming.MoviesUpcomingNavigation
import org.koin.dsl.module

val moviesUpcomingPresentationModules = module {
    factory<MoviesUpcomingNavigation> { MoviesUpcomingNavigationImpl() }
    factory{ MoviesUpcomingViewModel(getUpcomingMoviesUseCase = get()) }
}
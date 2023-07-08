package com.albuquerque.moviesupcoming.di

import com.albuquerque.moviesupcoming.domain.usecase.GetUpcomingMoviesUseCase
import com.albuquerque.moviesupcoming.navigation.MoviesUpcomingNavigationImpl
import com.albuquerque.moviesupcoming.presentation.MoviesUpcomingViewModel
import com.albuquerque.navigation.moviesupcoming.MoviesUpcomingNavigation
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val moviesUpcomingPresentationModules = module {
    factory<MoviesUpcomingNavigation> { MoviesUpcomingNavigationImpl() }
    viewModel {
        MoviesUpcomingViewModel(
            getUpcomingMoviesUseCase = GetUpcomingMoviesUseCase(repository = get()),
            toggleFavoriteUseCase = get(),
            scheduleNotificationsUseCase = get(),
            getFcmTokenUseCase = get(),
            tracker = get()
        )
    }
}
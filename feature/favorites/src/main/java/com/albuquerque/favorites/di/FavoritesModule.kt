package com.albuquerque.favorites.di

import com.albuquerque.favorites.domain.usecase.GetFavoriteMoviesUseCases
import com.albuquerque.favorites.navigation.FavoritesNavigationImpl
import com.albuquerque.favorites.presentation.FavoritesViewModel
import com.albuquerque.navigation.favorites.FavoritesNavigation
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val favoritesPresentationModules = module {
    factory<FavoritesNavigation>{ FavoritesNavigationImpl() }
    viewModel {
        FavoritesViewModel(
            getFavoriteMoviesUseCases = GetFavoriteMoviesUseCases(repository = get()),
            toggleFavoriteUseCase = get()
        )
    }
}
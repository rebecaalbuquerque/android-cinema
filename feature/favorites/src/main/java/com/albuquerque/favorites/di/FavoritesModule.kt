package com.albuquerque.favorites.di

import com.albuquerque.favorites.navigation.FavoritesNavigationImpl
import com.albuquerque.navigation.favorites.FavoritesNavigation
import org.koin.dsl.module

val favoritesPresentationModules = module {
    factory<FavoritesNavigation>{ FavoritesNavigationImpl() }
}
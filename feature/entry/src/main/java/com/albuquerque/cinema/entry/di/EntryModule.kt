package com.albuquerque.cinema.entry.di

import com.albuquerque.cinema.entry.navigation.EntryNavigationImpl
import com.albuquerque.navigation.entry.EntryNavigation
import org.koin.dsl.module

val entryPresentationModule = module {
    factory<EntryNavigation> { EntryNavigationImpl() }
}
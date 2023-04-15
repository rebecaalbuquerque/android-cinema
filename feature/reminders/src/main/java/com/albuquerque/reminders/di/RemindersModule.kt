package com.albuquerque.reminders.di

import com.albuquerque.navigation.reminders.RemindersNavigation
import com.albuquerque.reminders.navigation.RemindersNavigationImpl
import org.koin.dsl.module

val remindersPresentationModules = module {
    factory<RemindersNavigation>{ RemindersNavigationImpl() }
}
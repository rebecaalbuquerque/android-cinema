package com.albuquerque.reminders.di

import com.albuquerque.navigation.reminders.RemindersNavigation
import com.albuquerque.reminders.domain.usecase.GetRemindersUseCase
import com.albuquerque.reminders.navigation.RemindersNavigationImpl
import com.albuquerque.reminders.presentation.RemindersViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val remindersPresentationModules = module {
    factory<RemindersNavigation>{ RemindersNavigationImpl() }
    viewModel {
        RemindersViewModel(
            getRemindersUseCase = GetRemindersUseCase(get()),
            toggleReminderUseCase = get()
        )
    }
}
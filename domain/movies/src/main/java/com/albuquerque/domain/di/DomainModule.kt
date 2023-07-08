package com.albuquerque.domain.di

import com.albuquerque.domain.usecase.CreateMovieReminderUseCase
import com.albuquerque.domain.usecase.DeleteMovieReminderUseCase
import com.albuquerque.domain.usecase.ScheduleNotificationsUseCase
import com.albuquerque.domain.usecase.ToggleFavoriteUseCase
import com.albuquerque.domain.usecase.ToggleReminderUseCase
import org.koin.dsl.module

val moviesDomainModule = module {
    factory { ToggleFavoriteUseCase(repository = get()) }
    factory { CreateMovieReminderUseCase(repository = get()) }
    factory { DeleteMovieReminderUseCase(repository = get()) }
    factory {
        ToggleReminderUseCase(
            createMovieReminderUseCase = get(),
            deleteMovieReminderUseCase = get()
        )
    }
    factory { ScheduleNotificationsUseCase(repository = get()) }
}
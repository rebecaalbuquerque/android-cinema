package com.albuquerque.cinema.application

import android.app.Application
import com.albuquerque.analytics.analyticsModule
import com.albuquerque.auth.di.authDataModule
import com.albuquerque.auth.di.authDomainModule
import com.albuquerque.cinema.di.appModule
import com.albuquerque.common.di.notificationModule
import com.albuquerque.data.di.moviesDataModule
import com.albuquerque.data.di.networkModule
import com.albuquerque.domain.di.moviesDomainModule
import com.albuquerque.favorites.di.favoritesPresentationModules
import com.albuquerque.moviesupcoming.di.moviesUpcomingPresentationModules
import com.albuquerque.reminders.di.remindersPresentationModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class CinemaApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@CinemaApplication)
            modules(
                appModule,
                networkModule,
                notificationModule,
                analyticsModule,
                authDataModule,
                moviesDataModule,
                authDomainModule,
                moviesDomainModule,
                moviesUpcomingPresentationModules,
                favoritesPresentationModules,
                remindersPresentationModules
            )
        }
    }
}
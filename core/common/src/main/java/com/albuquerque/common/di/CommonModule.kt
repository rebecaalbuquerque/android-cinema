package com.albuquerque.common.di

import com.albuquerque.common.notification.CinemaNotificationManager
import com.albuquerque.common.notification.CinemaNotificationManagerImpl
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val notificationModule = module {
    single<CinemaNotificationManager> { CinemaNotificationManagerImpl(context = androidApplication()) }
}
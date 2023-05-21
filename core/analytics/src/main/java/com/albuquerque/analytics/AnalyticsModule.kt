package com.albuquerque.analytics

import android.annotation.SuppressLint
import com.google.firebase.analytics.FirebaseAnalytics
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

@SuppressLint("MissingPermission")
val analyticsModule = module {
    single { EventTracker(firebaseAnalytics = FirebaseAnalytics.getInstance(androidApplication())) }
}
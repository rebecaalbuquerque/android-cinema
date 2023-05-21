package com.albuquerque.analytics

import com.google.firebase.analytics.FirebaseAnalytics

class EventTracker(private val firebaseAnalytics: FirebaseAnalytics) {

    fun track(eventName: String, build: Event.() -> Unit = {}) {
        val event = Event().apply(build)

        firebaseAnalytics.logEvent(
            eventName,
            event.properties.toBundle()
        )
    }
}
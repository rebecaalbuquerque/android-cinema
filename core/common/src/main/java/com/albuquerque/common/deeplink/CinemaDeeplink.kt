package com.albuquerque.common.deeplink

import com.albuquerque.common.extension.toQueryParams

private const val BASE_URL = "cinema://cinema"

sealed class CinemaDeeplink(val url: String) {
    companion object {
        const val TAB_FAVORITES = "favorites"
        const val TAB_REMINDERS = "reminders"
    }

    object Home : CinemaDeeplink(BASE_URL)

    sealed class Favorites(destination: String) : CinemaDeeplink(BASE_URL + destination) {
        object Home : Favorites(destination = mapOf("tab" to TAB_FAVORITES, "" to "").toQueryParams())
    }

    sealed class Reminders(destination: String) : CinemaDeeplink(BASE_URL + destination) {
        object Home : Favorites(destination = mapOf("tab" to TAB_REMINDERS, "" to "").toQueryParams())

    }

}

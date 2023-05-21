package com.albuquerque.analytics

data class Event(val properties: Properties = Properties()) {

    fun properties(func: Properties.() -> Unit) {
        properties.func()
    }

}

sealed class EventName(val value: String) {

    sealed class Screen(screenValue: String) : EventName(screenValue) {
        object Viewed : Screen("Screen Viewed")
    }
}




package com.albuquerque.analytics

data class Event(val properties: Properties = Properties()) {

    fun properties(func: Properties.() -> Unit) {
        properties.func()
    }

}

sealed class EventName(val value: String) {

    sealed class Screen(screenValue: String) : EventName(screenValue) {
        object Viewed : Screen("Screen_Viewed")
    }

    sealed class Button(buttonValue: String) : EventName(buttonValue) {
        object Clicked : Button("Button_Clicked")
    }

    sealed class Notification(buttonValue: String) : EventName(buttonValue) {
        object Received : Button("Notification_Received")

        object Scheduled : Button("Notification_Scheduled")
    }
}




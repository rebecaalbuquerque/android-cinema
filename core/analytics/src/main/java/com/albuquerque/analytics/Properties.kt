package com.albuquerque.analytics

data class Properties(
    internal val propertiesMap: MutableMap<String, Any?> = mutableMapOf()
) {

    infix fun String.withValue(value: Any?) {
        propertiesMap[this] = value
    }

}

sealed class PropertiesName(val value: String) {

    sealed class Screen(screenValue: String) : PropertiesName(screenValue) {
        object Name : Screen("screen_name")
        object Status : Screen("screen_status")
    }

    sealed class Button(buttonValue: String) : PropertiesName(buttonValue) {
        object Screen : Button("screen_name")
        object Name : Button("button_name")
        object Status : Button("button_status")
    }

    sealed class Notification(notificationValue: String) : PropertiesName(notificationValue) {
        object Title : Notification("notification_title")
        object Text : Notification("notification_text")
        object Status : Notification("notification_status")
    }
}
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
        object Status : Screen("screen_name")
    }

}
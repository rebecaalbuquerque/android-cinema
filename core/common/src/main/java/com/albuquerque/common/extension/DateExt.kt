package com.albuquerque.common.extension

import java.text.SimpleDateFormat
import java.util.*

fun String.toBrazilianDate(): String {
    var date = ""

    try {
        date = SimpleDateFormat("yyyy-MM-dd", Locale("pt", "BR")).format(this.asDate())
    } catch (_: Exception) {}

    return date
}

fun String.asDate() : Date {
    val sdf = SimpleDateFormat("yyyy-MM-dd", Locale("pt", "BR"))
    return sdf.parse(this@asDate) ?: Calendar.getInstance().time
}

fun Date.isBiggerThan(currentTimeInMillis: Long): Boolean {
    return this@isBiggerThan.time > currentTimeInMillis
}
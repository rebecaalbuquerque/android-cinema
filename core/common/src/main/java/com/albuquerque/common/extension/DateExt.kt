package com.albuquerque.common.extension

import java.text.SimpleDateFormat
import java.util.*

fun String.formatDate(patternDate: String = "dd/MM/yyyy"): String {
    var date = this

    try {
        date = SimpleDateFormat(patternDate, Locale("pt", "BR")).format(this.asDate())
    } catch (_: Exception) { }

    return date
}

fun String.asDate(): Date {
    val sdf = SimpleDateFormat("yyyy-MM-dd", Locale("pt", "BR"))
    return sdf.parse(this@asDate) ?: Calendar.getInstance().time
}

fun Date.asDateString(patternDate: String = "dd/MM/yyyy"): String {
    val sdf = SimpleDateFormat(patternDate, Locale("pt", "BR"))
    return sdf.format(this)
}

fun Date.isBiggerThan(currentTimeInMillis: Long): Boolean {
    return this@isBiggerThan.time > currentTimeInMillis
}

fun Calendar.addTime(field: Int, amount: Int): Date {
    return this.apply {
        add(field, amount)
    }.time
}
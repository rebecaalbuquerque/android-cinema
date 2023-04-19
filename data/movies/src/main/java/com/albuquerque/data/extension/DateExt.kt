package com.albuquerque.data.extension

import java.text.SimpleDateFormat
import java.util.*

internal fun String.asDate() : Date {
    val sdf = SimpleDateFormat("yyyy-MM-dd", Locale("pt", "BR"))
    return sdf.parse(this@asDate) ?: Calendar.getInstance().time
}

internal fun Date.isBiggerThan(currentTimeInMillis: Long): Boolean {
    return this@isBiggerThan.time > currentTimeInMillis
}
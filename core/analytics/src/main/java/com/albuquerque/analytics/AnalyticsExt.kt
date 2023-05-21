package com.albuquerque.analytics

import android.os.Bundle

internal fun Properties.toBundle(): Bundle {

    return Bundle().apply {
        propertiesMap.forEach { param ->
            putString(
                param.key.lowercase(),
                (param.value as? String)?.ifBlank { null }?.lowercase()
            )
        }
    }
}
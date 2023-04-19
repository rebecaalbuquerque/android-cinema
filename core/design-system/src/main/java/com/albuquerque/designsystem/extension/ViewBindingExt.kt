package com.albuquerque.designsystem.extension

import androidx.viewbinding.ViewBinding

fun <T: ViewBinding> T?.bindSafely(block: T.() -> Unit) {
    if (this@bindSafely != null) {
        block()
    }
}
package com.albuquerque.designsystem.extension

import android.view.View
import android.view.ViewGroup

fun View.onClickListener(onClickListener: (() -> Unit)?) {
    if (onClickListener != null) {
        this@onClickListener.setOnClickListener {
            onClickListener()
        }
    }
}

fun ViewGroup.onClickListener(onClickListener: (() -> Unit)?) {
    if (onClickListener != null) {
        this@onClickListener.setOnClickListener {
            onClickListener()
        }
    }
}
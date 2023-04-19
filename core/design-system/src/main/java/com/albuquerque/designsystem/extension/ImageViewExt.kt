package com.albuquerque.designsystem.extension

import android.widget.ImageView
import androidx.annotation.DrawableRes

fun ImageView.setImageResource(@DrawableRes drawableRes: Int?) {
    if (drawableRes != null) {
        setImageResource(drawableRes)
    }
}
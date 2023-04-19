package com.albuquerque.designsystem.extension

import android.text.Spanned
import android.widget.TextView
import androidx.annotation.StringRes

fun TextView.setText(
    text: String? = null,
    @StringRes textRes: Int? = null,
    spannedText : Spanned? = null
) {
    when {
        text != null -> {
            this@setText.text = text
        }

        textRes != null -> {
            this@setText.setText(textRes)
        }

        spannedText != null -> {
            this@setText.text = spannedText
        }
    }
}
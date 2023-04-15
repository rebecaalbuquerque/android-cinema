package com.albuquerque.designsystem.extension

import android.widget.Toast
import androidx.fragment.app.Fragment

fun Fragment.toastLong(text: String) {
    Toast.makeText(context, text, Toast.LENGTH_LONG).show()
}

fun Fragment.toastShort(text: String) {
    Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
}
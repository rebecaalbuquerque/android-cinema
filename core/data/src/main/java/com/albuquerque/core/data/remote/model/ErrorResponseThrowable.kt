package com.albuquerque.core.data.remote.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ErrorResponseThrowable(
    @SerializedName("code") val code: String? = null,
    @SerializedName("message") override val message: String? = null,
    @Expose private val throwable: Throwable? = null
) : Throwable(message, throwable)
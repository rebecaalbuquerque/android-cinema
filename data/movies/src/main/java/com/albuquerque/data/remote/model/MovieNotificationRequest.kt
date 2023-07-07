package com.albuquerque.data.remote.model

import com.google.gson.annotations.SerializedName

data class MovieNotificationRequest(
    @SerializedName("movieId")
    val movieId: Int,

    @SerializedName("movieName")
    val movieName: String,

    @SerializedName("releaseDate")
    val releaseDate: String,

    @SerializedName("deviceToken")
    val deviceToken: String,

    @SerializedName("deviceUuid")
    val deviceUuid: String,

)
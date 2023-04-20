package com.albuquerque.common.extension

import java.net.URLEncoder

fun Map<String, String>.toQueryParams(): String {
    val query = "?"
    val params: List<String> = this.entries
        .map { (key, value) ->
            if (key.isBlank() || value.isBlank()) {
                ""
            } else {
                "${URLEncoder.encode(key, "utf-8")}=${URLEncoder.encode(value, "utf-8")}"
            }
        }
    return query + params.joinToString("&")
}
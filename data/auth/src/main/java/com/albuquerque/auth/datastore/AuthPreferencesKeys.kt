package com.albuquerque.auth.datastore

import androidx.datastore.preferences.core.stringPreferencesKey

object AuthPreferencesKeys {
    val FCM_TOKEN = stringPreferencesKey("fcm_token")
    val DEVICE_UUID = stringPreferencesKey("device_uuid")
}
package com.albuquerque.auth.datasource

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.albuquerque.auth.datastore.AuthPreferencesKeys
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AuthLocalDataSourceImpl(
    private val dataStore: DataStore<Preferences>
) : AuthLocalDataSource {

    override suspend fun saveFcmToken(token: String) {
        dataStore.edit { preferences ->
            preferences[AuthPreferencesKeys.FCM_TOKEN] = token
        }
    }

    override fun getFcmToken(): Flow<String?> {
        return dataStore.data.map { preferences ->
            preferences[AuthPreferencesKeys.FCM_TOKEN]
        }
    }

    override suspend fun saveDeviceUuid(uuid: String) {
        dataStore.edit { preferences ->
            preferences[AuthPreferencesKeys.DEVICE_UUID] = uuid
        }
    }

    override fun getDeviceUuid(): Flow<String?> {
        return dataStore.data.map { preferences ->
            preferences[AuthPreferencesKeys.DEVICE_UUID]
        }
    }
}
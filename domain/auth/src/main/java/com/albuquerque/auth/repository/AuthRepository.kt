package com.albuquerque.auth.repository

import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    fun getFcmToken(): Flow<String?>

    suspend fun saveFcmToken(token: String)

    fun getDeviceUuid(): Flow<String?>

    suspend fun saveDeviceUuid(uuid: String)
}
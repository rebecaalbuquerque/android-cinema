package com.albuquerque.auth.datasource

import kotlinx.coroutines.flow.Flow

interface AuthLocalDataSource {

    fun getUser(): Flow<String?>

    suspend fun saveUser(user: String)

    fun getFcmToken(): Flow<String?>

    suspend fun saveFcmToken(token: String)

    fun getDeviceUuid(): Flow<String?>

    suspend fun saveDeviceUuid(uuid: String)
}
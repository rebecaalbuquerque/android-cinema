package com.albuquerque.auth.repository

import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    suspend fun saveUser(user: String)

    fun getUser(): Flow<String?>

    fun getFcmToken(): Flow<String?>

    suspend fun saveFcmToken(token: String)

    fun getDeviceUuid(): Flow<String?>

    suspend fun saveDeviceUuid(uuid: String)
}
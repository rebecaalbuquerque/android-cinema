package com.albuquerque.auth.repository

import com.albuquerque.auth.datasource.AuthLocalDataSource
import kotlinx.coroutines.flow.Flow

class AuthRepositoryImpl(
    private val localDataSource: AuthLocalDataSource
) : AuthRepository {

    override fun getFcmToken(): Flow<String?> {
        return localDataSource.getFcmToken()
    }

    override suspend fun saveFcmToken(token: String) {
        localDataSource.saveFcmToken(token)
    }

    override fun getDeviceUuid(): Flow<String?> {
        return localDataSource.getDeviceUuid()
    }

    override suspend fun saveDeviceUuid(uuid: String) {
        localDataSource.saveDeviceUuid(uuid)
    }
}
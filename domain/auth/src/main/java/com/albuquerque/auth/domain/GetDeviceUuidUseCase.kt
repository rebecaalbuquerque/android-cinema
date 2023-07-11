package com.albuquerque.auth.domain

import com.albuquerque.auth.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetDeviceUuidUseCase(
    private val repository: AuthRepository
) {

    operator fun invoke(): Flow<String> {
        return repository.getDeviceUuid().map { it.orEmpty() }
    }
}
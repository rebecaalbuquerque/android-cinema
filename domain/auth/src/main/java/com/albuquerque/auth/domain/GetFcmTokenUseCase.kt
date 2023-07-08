package com.albuquerque.auth.domain

import com.albuquerque.auth.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetFcmTokenUseCase(
    private val repository: AuthRepository
) {

    operator fun invoke(): Flow<String> {
        return repository.getFcmToken().map { it.orEmpty() }
    }
}
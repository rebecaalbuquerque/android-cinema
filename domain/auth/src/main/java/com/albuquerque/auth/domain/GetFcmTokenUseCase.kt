package com.albuquerque.auth.domain

import com.albuquerque.auth.repository.AuthRepository
import kotlinx.coroutines.flow.Flow

class GetFcmTokenUseCase(
    private val repository: AuthRepository
) {

    operator fun invoke(): Flow<String?> {
        return repository.getFcmToken()
    }
}
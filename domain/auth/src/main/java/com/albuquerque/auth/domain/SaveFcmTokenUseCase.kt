package com.albuquerque.auth.domain

import com.albuquerque.auth.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SaveFcmTokenUseCase(
    private val repository: AuthRepository
) {

    operator fun invoke(token: String, isNewToken: Boolean = false): Flow<Unit> = flow {

        repository.getFcmToken().collect { currentToken ->

            if (currentToken == null || isNewToken) {
                repository.saveFcmToken(token)
                emit(Unit)
            } else {
                emit(Unit)
            }

        }
    }
}
package com.albuquerque.auth.domain

import com.albuquerque.auth.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SaveUserUseCase(
    private val repository: AuthRepository
) {

    operator fun invoke(user: String): Flow<Unit> = flow {
        repository.saveUser(user)
        emit(Unit)
    }
}
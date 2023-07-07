package com.albuquerque.auth.domain

import com.albuquerque.auth.repository.AuthRepository
import kotlinx.coroutines.flow.flow

class SaveDeviceUuidUseCase(
    private val repository: AuthRepository
) {

    operator fun invoke(uuid: String) = flow {
        repository.getDeviceUuid().collect { currentUuid ->

            if (currentUuid == null) {
                repository.saveDeviceUuid(uuid)
                emit(Unit)
            } else {
                emit(Unit)
            }
        }
    }
}
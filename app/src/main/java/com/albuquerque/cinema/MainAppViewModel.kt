package com.albuquerque.cinema

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.albuquerque.auth.domain.SaveDeviceUuidUseCase
import com.albuquerque.auth.domain.SaveFcmTokenUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class MainAppViewModel(
    private val saveFcmTokenUseCase: SaveFcmTokenUseCase,
    private val saveDeviceUuidUseCase: SaveDeviceUuidUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    fun generateFcmToken(token: String) = viewModelScope.launch {
        saveFcmTokenUseCase(token = token)
            .flowOn(dispatcher)
            .catch {
                // todo: improve
            }
            .collect { }
    }

    fun generateDeviceUuid(uuid: String) = viewModelScope.launch {
        saveDeviceUuidUseCase(uuid = uuid)
            .flowOn(dispatcher)
            .catch {
                // todo: improve
            }
            .collect { }
    }
}
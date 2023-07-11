package com.albuquerque.auth.di

import com.albuquerque.auth.domain.GetDeviceUuidUseCase
import com.albuquerque.auth.domain.GetFcmTokenUseCase
import com.albuquerque.auth.domain.SaveDeviceUuidUseCase
import com.albuquerque.auth.domain.SaveFcmTokenUseCase
import org.koin.dsl.module

val authDomainModule = module {
    factory { SaveFcmTokenUseCase(get()) }
    factory { GetFcmTokenUseCase(get()) }
    factory { SaveDeviceUuidUseCase(get()) }
    factory { GetDeviceUuidUseCase(get()) }
}
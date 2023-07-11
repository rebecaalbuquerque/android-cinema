package com.albuquerque.core.data.remote.model

class NetworkException(
    override val message: String = "Verifique sua conexão e tente novamente."
) : Exception()
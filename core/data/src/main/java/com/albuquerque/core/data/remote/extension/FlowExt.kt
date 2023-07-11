package com.albuquerque.core.data.remote.extension

import com.albuquerque.core.data.remote.model.ErrorResponseThrowable
import com.albuquerque.core.data.remote.model.NetworkException
import com.google.gson.Gson
import java.io.InterruptedIOException
import java.net.SocketException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import retrofit2.HttpException

private const val UNEXPECTED_ERROR_MESSAGE = "Ocorreu um erro inesperado, tente novamente."

private fun HttpException.parseError(): ErrorResponseThrowable {
    return try {
        val convertErrorBody: ErrorResponseThrowable? = Gson().fromJson(
            response()?.errorBody()?.string(),
            ErrorResponseThrowable::class.java
        )
        convertErrorBody?.copy(throwable = this) ?: ErrorResponseThrowable(
            message = UNEXPECTED_ERROR_MESSAGE,
            throwable = null
        )
    } catch (e: java.lang.Exception) {
        ErrorResponseThrowable(
            message = UNEXPECTED_ERROR_MESSAGE,
            throwable = this
        )
    }
}

private fun Throwable?.toRequestThrowable(): Throwable {
    return when (this) {
        is HttpException -> parseError()
        is UnknownHostException,
        is TimeoutException,
        is InterruptedIOException,
        is SocketException -> NetworkException()
        else -> this ?: Throwable(UNEXPECTED_ERROR_MESSAGE)
    }
}

fun <T> emitRequestFlow(request: suspend () -> T): Flow<T> {
    return flow {
        this.emit(request())
    }.catch { throwable ->
        throw throwable.toRequestThrowable()
    }
}

suspend fun <T> emitRequestResult(
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    request: suspend () -> T
): Result<T> {
    return withContext(dispatcher) {
        try {
            Result.success(request())
        } catch (throwable: Throwable) {
            Result.failure(throwable.toRequestThrowable())
        }
    }
}
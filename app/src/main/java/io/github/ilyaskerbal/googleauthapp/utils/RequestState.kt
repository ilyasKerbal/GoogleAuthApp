package io.github.ilyaskerbal.googleauthapp.utils

sealed class RequestState<out T> {
    object Idle: RequestState<Nothing>()
    object Loading: RequestState<Nothing>()
    data class Success<T>(val data: T): RequestState<T>()
    data class Error(
        val t: Throwable,
        val consumed: Boolean = false
    ): RequestState<Nothing>()
}
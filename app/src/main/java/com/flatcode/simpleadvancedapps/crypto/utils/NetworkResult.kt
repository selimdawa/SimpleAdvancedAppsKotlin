package com.flatcode.simpleadvancedapps.crypto.utils

sealed class NetworkResult<out T> {
    data class Success<out T>(val data: T) : NetworkResult<T>()
    data class Error(val message1: Boolean, val message: String?) : NetworkResult<Nothing>()
    class Loading<out T> : NetworkResult<T>()
}
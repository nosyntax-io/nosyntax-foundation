package io.nosyntax.foundation.core.util

enum class Exception(val message: String) {
    NetworkError("A network error occurred."),
    ServerError("The server responded with an unexpected status."),
    InvalidRequest("The request was malformed or invalid."),
    LocalDataError("Failed to load the local configuration data.")
}

sealed class Resource<T>(val data: T? = null, val message: String? = null) {
    class Loading<T>(val isLoading: Boolean = true) : Resource<T>(null)
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(exception: Exception, data: T? = null) : Resource<T>(data, exception.message)
}
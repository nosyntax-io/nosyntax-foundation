package io.nosyntax.foundation.data.source.remote.factory

import io.nosyntax.foundation.core.Constants
import io.nosyntax.foundation.core.utility.Connectivity
import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit

sealed class Auth {
    data class Bearer(val token: String): Auth()
    data class Basic(val username: String, val password: String): Auth()
}

class ServiceFactory(
    private val baseUrl: String,
    private val auth: Auth? = null,
    private val timeout: Long = 10000
) {
    private val okHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(createInterceptor())
            .readTimeout(timeout, TimeUnit.MILLISECONDS)
            .writeTimeout(timeout, TimeUnit.MILLISECONDS)
            .build()
    }

    fun <T> create(service: Class<T>): T =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(service)

    private fun createInterceptor() = Interceptor { chain ->
        if (!Connectivity.getInstance().isOnline()) {
            throw IOException(Constants.INTERNET_CONNECTION_EXCEPTION)
        }

        val requestBuilder = chain.request().newBuilder()

        auth?.let {
            val authHeader = when (it) {
                is Auth.Bearer -> "Bearer ${it.token}"
                is Auth.Basic -> Credentials.basic(it.username, it.password)
            }
            requestBuilder.header("Authorization", authHeader)
        }

        chain.proceed(requestBuilder.build())
    }
}
package io.nosyntax.template.android.data.source.remote.factory

import io.nosyntax.template.android.core.Constants
import io.nosyntax.template.android.core.utility.Connectivity
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

class ApiServiceFactory(
    private val baseUrl: String,
    private val auth: Auth? = null,
    private val timeout: Long = 10
) {
    private val okHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(createInterceptor())
            .readTimeout(timeout, TimeUnit.SECONDS)
            .writeTimeout(timeout, TimeUnit.SECONDS)
            .build()
    }

    fun <T> create(clazz: Class<T>): T {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(clazz)
    }

    private fun createInterceptor(): Interceptor {
        return Interceptor { chain ->
            if (!Connectivity.getInstance().isOnline()) {
                throw IOException(Constants.INTERNET_CONNECTION_EXCEPTION)
            } else {
                val requestBuilder = chain.request().newBuilder()

                when (auth) {
                    is Auth.Bearer -> {
                        val authorization = "Bearer ${auth.token}"
                        requestBuilder.header("Authorization", authorization)
                    }
                    is Auth.Basic -> {
                        val authorization = Credentials.basic(username = auth.username, password = auth.password)
                        requestBuilder.header("Authorization", authorization)
                    }
                    else -> {

                    }
                }
                chain.proceed(requestBuilder.build())
            }
        }
    }
}
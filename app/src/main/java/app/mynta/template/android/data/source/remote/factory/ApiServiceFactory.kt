package app.mynta.template.android.data.source.remote.factory

import app.mynta.template.android.core.utility.ConnectivityInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiServiceFactory(private val baseUrl: String, private val timeout: Long) {
    private val okHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(ConnectivityInterceptor())
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
}
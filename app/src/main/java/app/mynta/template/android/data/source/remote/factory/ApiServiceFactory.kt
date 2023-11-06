package app.mynta.template.android.data.source.remote.factory

import app.mynta.template.android.core.Constants
import app.mynta.template.android.core.utility.Connectivity
import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit

data class BasicAuthCredentials(
    val username: String,
    val password: String
)

class ApiServiceFactory(
    private val baseUrl: String,
    private val basicAuth: BasicAuthCredentials? = null,
    private val timeout: Long,
) {
    private val okHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(ApiInterceptor(basicAuth = basicAuth))
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

class ApiInterceptor(private val basicAuth: BasicAuthCredentials?) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!Connectivity.getInstance().isOnline()) {
            throw IOException(Constants.INTERNET_CONNECTION_EXCEPTION)
        } else {
            val requestBuilder = chain.request().newBuilder()

            basicAuth?.let { credentials ->
                val authHeader = Credentials.basic(credentials.username, credentials.password)
                requestBuilder.header("Authorization", authHeader)
            }
            return chain.proceed(requestBuilder.build())
        }
    }
}
package app.mynta.template.android.data.source.remote

import app.mynta.template.android.BuildConfig
import app.mynta.template.android.core.Constants
import app.mynta.template.android.core.utility.Connectivity
import app.mynta.template.android.data.source.remote.dto.app_config.AppConfigDto
import app.mynta.template.android.data.source.remote.factory.ApiServiceFactory
import okhttp3.Interceptor
import okhttp3.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import java.io.IOException

interface CoreAPI {
    @FormUrlEncoded
    @POST("app_config.php")
    suspend fun appConfig(
        @Field("access_token") accessToken: String = ACCESS_TOKEN
    ): AppConfigDto

    companion object {
        private const val BASE_URL = "https://api.mynta.app/v1.4beta/"
        private const val ACCESS_TOKEN = BuildConfig.SERVER_ACCESS_TOKEN

        fun getInstance(): CoreAPI {
            return ApiServiceFactory(
                baseUrl = BASE_URL,
                timeout = 10,
                interceptor = object: Interceptor {
                    override fun intercept(chain: Interceptor.Chain): Response {
                        if (!Connectivity.getInstance().isOnline()) {
                            throw IOException(Constants.INTERNET_CONNECTION_EXCEPTION)
                        } else {
                            val requestBuilder = chain.request().newBuilder()
                            requestBuilder.header("Authorization", "Bearer ${BuildConfig.SERVER_AUTH_TOKEN}")

                            return chain.proceed(requestBuilder.build())
                        }
                    }
                }
            ).create(CoreAPI::class.java)
        }
    }
}
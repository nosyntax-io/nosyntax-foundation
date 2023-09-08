package app.mynta.template.android.data.source.remote

import app.mynta.template.android.BuildConfig
import app.mynta.template.android.core.utility.ConnectivityInterceptor
import app.mynta.template.android.data.source.remote.dto.app_config.AppConfigDto
import app.mynta.template.android.data.source.remote.dto.policies.PoliciesDto
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import java.util.concurrent.TimeUnit

interface APIService {
    @FormUrlEncoded
    @POST("request_app_config.inc.php")
    suspend fun appConfig(
        @Field("access_token") accessToken: String = API_ACCESS_TOKEN
    ): AppConfigDto

    @FormUrlEncoded
    @POST("request_policies.inc.php")
    suspend fun policies(
        @Field("access_token") accessToken: String = API_ACCESS_TOKEN
    ): PoliciesDto

    companion object {
        private const val API_BASE_URL = "https://api.mynta.app/v1/"
        private const val API_ACCESS_TOKEN = BuildConfig.ACCESS_TOKEN

        private val okHttpClient by lazy {
            OkHttpClient.Builder()
                .addInterceptor(ConnectivityInterceptor())
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .build()
        }

        fun create(): APIService {
            return Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(APIService::class.java)
        }
    }
}
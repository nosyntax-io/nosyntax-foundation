package app.mynta.template.android.data.source.remote

import app.mynta.template.android.BuildConfig
import app.mynta.template.android.core.utility.ConnectivityInterceptor
import app.mynta.template.android.data.source.remote.dto.app_config.AppConfigDto
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface APIService {
    @FormUrlEncoded
    @POST("exp_config.inc.php")
    suspend fun appConfig(
        @Field("access_token") accessToken: String = API_ACCESS_TOKEN
    ): AppConfigDto

    companion object {
        private const val API_BASE_URL = "https://api.mynta.app/v1/service/"
        private const val API_ACCESS_TOKEN = BuildConfig.ACCESS_TOKEN

        private val okHttpClient by lazy {
            OkHttpClient.Builder()
                .addInterceptor(ConnectivityInterceptor())
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
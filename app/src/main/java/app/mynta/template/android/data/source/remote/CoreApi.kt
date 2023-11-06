package app.mynta.template.android.data.source.remote

import app.mynta.template.android.BuildConfig
import app.mynta.template.android.data.source.remote.dto.app_config.AppConfigDto
import app.mynta.template.android.data.source.remote.factory.ApiServiceFactory
import app.mynta.template.android.data.source.remote.factory.BasicAuthCredentials
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

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
                basicAuth = BasicAuthCredentials(
                    username = BuildConfig.SERVER_BASIC_AUTH_USERNAME,
                    password = BuildConfig.SERVER_BASIC_AUTH_PASSWORD
                ),
                timeout = 10
            ).create(CoreAPI::class.java)
        }
    }
}
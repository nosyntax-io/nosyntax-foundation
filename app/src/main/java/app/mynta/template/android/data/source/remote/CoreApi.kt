package app.mynta.template.android.data.source.remote

import app.mynta.template.android.BuildConfig
import app.mynta.template.android.data.source.remote.dto.app_config.AppConfigDto
import app.mynta.template.android.data.source.remote.dto.policies.PoliciesDto
import app.mynta.template.android.data.source.remote.factory.ApiServiceFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface CoreAPI {
    @FormUrlEncoded
    @POST("request_app_config.inc.php")
    suspend fun appConfig(
        @Field("access_token") accessToken: String = ACCESS_TOKEN
    ): AppConfigDto

    @FormUrlEncoded
    @POST("request_policies.inc.php")
    suspend fun policies(
        @Field("access_token") accessToken: String = ACCESS_TOKEN
    ): PoliciesDto

    companion object {
        private const val BASE_URL = "https://api.mynta.app/v1/"
        private const val ACCESS_TOKEN = BuildConfig.SERVER_ACCESS_TOKEN

        fun getInstance(): CoreAPI {
            return ApiServiceFactory(baseUrl = BASE_URL, timeout = 10)
                .create(CoreAPI::class.java)
        }
    }
}
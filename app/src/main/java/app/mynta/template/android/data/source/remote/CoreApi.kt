package app.mynta.template.android.data.source.remote

import app.mynta.template.android.BuildConfig
import app.mynta.template.android.data.source.remote.dto.app_config.AppConfigDto
import app.mynta.template.android.data.source.remote.dto.policies.PoliciesDto
import app.mynta.template.android.data.source.remote.factory.ApiServiceFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface CoreApi {
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

        fun getInstance(): CoreApi {
            return ApiServiceFactory(baseUrl = API_BASE_URL)
                .create(CoreApi::class.java)
        }
    }
}
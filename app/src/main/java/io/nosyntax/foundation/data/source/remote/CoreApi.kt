package io.nosyntax.foundation.data.source.remote

import io.nosyntax.foundation.BuildConfig
import io.nosyntax.foundation.data.source.remote.dto.app_config.AppConfigDto
import io.nosyntax.foundation.data.source.remote.factory.ApiServiceFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface CoreAPI {
    @FormUrlEncoded
    @POST("app_config")
    suspend fun appConfig(
        @Field("access_token") accessToken: String = ACCESS_TOKEN
    ): AppConfigDto

    companion object {
        private const val BASE_URL = "https://api.nosyntax.io/v1.0/"
        private const val ACCESS_TOKEN = BuildConfig.DYNAMIC_CONFIG_ACCESS_TOKEN

        fun getInstance(): CoreAPI {
            return ApiServiceFactory(
                baseUrl = BASE_URL,
                timeout = 10
            ).create(CoreAPI::class.java)
        }
    }
}
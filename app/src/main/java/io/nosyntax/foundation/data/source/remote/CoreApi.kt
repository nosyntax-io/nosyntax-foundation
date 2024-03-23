package io.nosyntax.foundation.data.source.remote

import io.nosyntax.foundation.BuildConfig
import io.nosyntax.foundation.data.source.remote.dto.app_config.AppConfigDto
import io.nosyntax.foundation.data.source.remote.factory.ApiServiceFactory
import io.nosyntax.foundation.data.source.remote.factory.Auth
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
        private const val AUTH_TOKEN = BuildConfig.SERVER_AUTH_TOKEN
        private const val ACCESS_TOKEN = BuildConfig.SERVER_ACCESS_TOKEN

        fun getInstance(): CoreAPI {
            return ApiServiceFactory(
                baseUrl = BASE_URL,
                auth = Auth.Bearer(AUTH_TOKEN),
                timeout = 10
            ).create(CoreAPI::class.java)
        }
    }
}
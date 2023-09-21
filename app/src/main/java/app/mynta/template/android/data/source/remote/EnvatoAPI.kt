package app.mynta.template.android.data.source.remote

import app.mynta.template.android.BuildConfig
import app.mynta.template.android.data.source.remote.dto.envato.EnvatoAuthorSaleDto
import app.mynta.template.android.data.source.remote.factory.ApiServiceFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query

interface EnvatoAPI {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json")
    @GET("v3/market/author/sale")
    suspend fun authorSale(
        @Header("Authorization") authorization: String = "Bearer $PERSONAL_TOKEN",
        @Query("code") purchaseCode: String
    ): EnvatoAuthorSaleDto

    companion object {
        private const val BASE_URL = "https://api.envato.com/"
        private const val PERSONAL_TOKEN = BuildConfig.ENVATO_TOKEN

        fun getInstance(): EnvatoAPI {
            return ApiServiceFactory(baseUrl = BASE_URL, timeout = 30)
                .create(EnvatoAPI::class.java)
        }
    }
}
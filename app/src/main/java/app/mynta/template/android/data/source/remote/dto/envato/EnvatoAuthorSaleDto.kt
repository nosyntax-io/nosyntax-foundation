package app.mynta.template.android.data.source.remote.dto.envato

import com.google.gson.annotations.SerializedName

data class EnvatoAuthorSaleDto(
    @SerializedName("license")
    val license: String,
    @SerializedName("amount")
    val amount: String,
    @SerializedName("sold_at")
    val soldAt: String,
    @SerializedName("buyer")
    val buyer: String,
    @SerializedName("item")
    val item: Item) {

    data class Item(
        @SerializedName("id")
        val id: Int,
        @SerializedName("name")
        val name: String
    )
}
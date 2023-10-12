package app.mynta.template.android.data.source.remote.dto.app_config

import com.google.gson.annotations.SerializedName

data class SideMenuConfigDto(
    @SerializedName("display")
    val display: Boolean,
    @SerializedName("background")
    val background: String,
    @SerializedName("header")
    val header: Header,
    @SerializedName("items")
    val items: List<Item>) {

    data class Header(
        @SerializedName("display")
        val display: Boolean,
        @SerializedName("image")
        val image: String
    )

    data class Item(
        @SerializedName("route")
        val route: String,
        @SerializedName("label")
        val label: String?,
        @SerializedName("icon")
        val icon: String?,
        @SerializedName("deeplink")
        val deeplink: String,
    )
}
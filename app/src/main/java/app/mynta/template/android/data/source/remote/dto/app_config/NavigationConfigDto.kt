package app.mynta.template.android.data.source.remote.dto.app_config

import com.google.gson.annotations.SerializedName

data class NavigationConfigDto(
    @SerializedName("default")
    val default: String,
    @SerializedName("items")
    val items: List<NavigationItem>) {

    data class NavigationItem(
        @SerializedName("item_id")
        val itemId: Int,
        @SerializedName("role")
        val role: String,
        @SerializedName("label")
        val label: String?,
        @SerializedName("icon")
        val icon: String?,
        @SerializedName("route")
        val route: String,
    )
}
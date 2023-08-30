package app.mynta.template.android.data.source.remote.dto

import com.google.gson.annotations.SerializedName

data class ConfigurationDto(
    @SerializedName("navigation_items")
    val navigationItems: List<NavigationItem>) {

    data class NavigationItem(
        @SerializedName("id")
        val id: String,
        @SerializedName("type")
        val type: String,
        @SerializedName("label")
        val label: String,
        @SerializedName("icon")
        val icon: String
    )
}
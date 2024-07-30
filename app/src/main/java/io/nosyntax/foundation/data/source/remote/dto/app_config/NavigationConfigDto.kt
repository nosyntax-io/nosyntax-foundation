package io.nosyntax.foundation.data.source.remote.dto.app_config

import com.google.gson.annotations.SerializedName

data class NavigationConfigDto(
    @SerializedName("default")
    val default: String,
    @SerializedName("items")
    val items: List<NavigationItem>) {

    data class NavigationItem(
        @SerializedName("id")
        val id: String,
        @SerializedName("type")
        val type: String,
        @SerializedName("label")
        val label: String?,
        @SerializedName("icon")
        val icon: String?,
        @SerializedName("url")
        val url: String?
    )
}
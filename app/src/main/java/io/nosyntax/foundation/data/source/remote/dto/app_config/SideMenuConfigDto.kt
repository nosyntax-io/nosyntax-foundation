package io.nosyntax.foundation.data.source.remote.dto.app_config

import com.google.gson.annotations.SerializedName

data class SideMenuConfigDto(
    @SerializedName("visible")
    val visible: Boolean,
    @SerializedName("background")
    val background: String,
    @SerializedName("header")
    val header: Header,
    @SerializedName("items")
    val items: List<Item>
) {
    data class Header(
        @SerializedName("visible")
        val visible: Boolean,
        @SerializedName("image")
        val image: String
    )

    data class Item(
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
package io.nosyntax.foundation.data.source.remote.dto.app_config

import com.google.gson.annotations.SerializedName

data class SideMenuConfigDto(
    @SerializedName("visible")
    val visible: Boolean,
    @SerializedName("background")
    val background: String,
    @SerializedName("header")
    val header: Header
) {
    data class Header(
        @SerializedName("visible")
        val visible: Boolean,
        @SerializedName("image")
        val image: String
    )
}
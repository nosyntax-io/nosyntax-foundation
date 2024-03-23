package io.nosyntax.foundation.data.source.remote.dto.app_config

import com.google.gson.annotations.SerializedName

data class AppBarConfigDto(
    @SerializedName("display")
    val display: Boolean,
    @SerializedName("background")
    val background: String,
    @SerializedName("title")
    val title: Title
) {
    data class Title(
        @SerializedName("display")
        val display: Boolean,
        @SerializedName("position")
        val position: String
    )
}
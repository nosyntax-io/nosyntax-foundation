package io.nosyntax.foundation.data.source.remote.dto.app_config

import com.google.gson.annotations.SerializedName

data class AppBarConfigDto(
    @SerializedName("visible")
    val visible: Boolean,
    @SerializedName("background")
    val background: String,
    @SerializedName("title")
    val title: Title
) {
    data class Title(
        @SerializedName("visible")
        val visible: Boolean,
        @SerializedName("alignment")
        val alignment: String
    )
}
package io.nosyntax.foundation.data.source.remote.dto.app_config

import com.google.gson.annotations.SerializedName

data class LoadingIndicatorConfigDto(
    @SerializedName("display")
    val display: Boolean,
    @SerializedName("animation")
    val animation: String,
    @SerializedName("background")
    val background: String,
    @SerializedName("color")
    val color: String
)
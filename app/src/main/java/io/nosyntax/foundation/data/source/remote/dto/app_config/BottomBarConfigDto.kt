package io.nosyntax.foundation.data.source.remote.dto.app_config

import com.google.gson.annotations.SerializedName

data class BottomBarConfigDto(
    @SerializedName("display")
    val display: Boolean,
    @SerializedName("background")
    val background: String,
    @SerializedName("label")
    val label: String
)
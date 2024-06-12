package io.nosyntax.foundation.data.source.remote.dto.app_config

import com.google.gson.annotations.SerializedName

data class BottomBarConfigDto(
    @SerializedName("visible")
    val visible: Boolean,
    @SerializedName("background")
    val background: String,
    @SerializedName("label")
    val label: String
)
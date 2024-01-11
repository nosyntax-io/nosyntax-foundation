package io.nosyntax.foundation.data.source.remote.dto.app_config

import com.google.gson.annotations.SerializedName

data class ModulesConfigDto(
    @SerializedName("webkit")
    val webkit: WebKitConfigDto
)
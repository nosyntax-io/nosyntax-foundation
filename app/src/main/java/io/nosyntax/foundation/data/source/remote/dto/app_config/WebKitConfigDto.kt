package io.nosyntax.foundation.data.source.remote.dto.app_config

import com.google.gson.annotations.SerializedName

data class WebKitConfigDto(
    @SerializedName("user_agent")
    val userAgent: UserAgent) {

    data class UserAgent(
        @SerializedName("android")
        val android: String
    )
}
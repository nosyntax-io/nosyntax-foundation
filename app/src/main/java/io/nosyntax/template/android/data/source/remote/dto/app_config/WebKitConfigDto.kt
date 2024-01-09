package io.nosyntax.template.android.data.source.remote.dto.app_config

import com.google.gson.annotations.SerializedName

data class WebKitConfigDto(
    @SerializedName("user_agent")
    val userAgent: UserAgent,
    @SerializedName("custom_css")
    val customCss: String) {

    data class UserAgent(
        @SerializedName("android")
        val android: String
    )
}
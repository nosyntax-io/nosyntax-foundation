package app.mynta.template.android.data.source.remote.dto.app_config

import com.google.gson.annotations.SerializedName

data class WebKitConfigDto(
    @SerializedName("custom_css")
    val customCss: String
)
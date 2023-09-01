package app.mynta.template.android.data.source.remote.dto.app_config

import com.google.gson.annotations.SerializedName

data class ThemeColorsConfigDto(
    @SerializedName("primary")
    val primary: String,
    @SerializedName("secondary")
    val secondary: String,
    @SerializedName("highlight")
    val highlight: String
)
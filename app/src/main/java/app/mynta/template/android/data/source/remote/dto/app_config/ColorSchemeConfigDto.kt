package app.mynta.template.android.data.source.remote.dto.app_config

import com.google.gson.annotations.SerializedName

data class ColorSchemeConfigDto(
    @SerializedName("primary")
    val primary: String,
    @SerializedName("primary_container")
    val primaryContainer: String,
    @SerializedName("secondary")
    val secondary: String,
    @SerializedName("secondary_container")
    val secondaryContainer: String,
    @SerializedName("background_light")
    val backgroundLight: String,
    @SerializedName("on_background_light")
    val onBackgroundLight: String,
    @SerializedName("surface_light")
    val surfaceLight: String,
    @SerializedName("on_surface_light")
    val onSurfaceLight: String,
    @SerializedName("background_dark")
    val backgroundDark: String,
    @SerializedName("on_background_dark")
    val onBackgroundDark: String,
    @SerializedName("surface_dark")
    val surfaceDark: String,
    @SerializedName("on_surface_dark")
    val onSurfaceDark: String
)
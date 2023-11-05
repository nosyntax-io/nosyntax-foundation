package app.mynta.template.android.data.source.remote.dto.app_config

import com.google.gson.annotations.SerializedName

data class ThemeConfigDto(
    @SerializedName("color_scheme")
    val colorScheme: ColorSchemeConfigDto,
    @SerializedName("typography")
    val typography: TypographyConfigDto,
    @SerializedName("settings")
    val settings: SettingsConfigDto) {

    data class SettingsConfigDto(
        @SerializedName("dark_mode")
        val darkMode: Boolean
    )
}
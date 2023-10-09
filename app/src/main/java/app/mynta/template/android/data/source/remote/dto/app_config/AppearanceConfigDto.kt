package app.mynta.template.android.data.source.remote.dto.app_config

import com.google.gson.annotations.SerializedName

data class AppearanceConfigDto(
    @SerializedName("theme_colors")
    val themeColors: ThemeColorsConfigDto,
    @SerializedName("typography")
    val typography: TypographyConfigDto
)
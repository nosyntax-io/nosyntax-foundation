package io.nosyntax.foundation.data.source.remote.dto.app_config

import com.google.gson.annotations.SerializedName

data class ThemeDto(
    @SerializedName("color_scheme")
    val colorScheme: ColorScheme,
    @SerializedName("typography")
    val typography: Typography,
    @SerializedName("dark_mode")
    val darkMode: Boolean
) {
    data class ColorScheme(
        @SerializedName("primary")
        val primary: String,
        @SerializedName("on_primary")
        val onPrimary: String,
        @SerializedName("secondary")
        val secondary: String,
        @SerializedName("on_secondary")
        val onSecondary: String,
        @SerializedName("background_light")
        val backgroundLight: String,
        @SerializedName("on_background_light")
        val onBackgroundLight: String,
        @SerializedName("surface_light")
        val surfaceLight: String,
        @SerializedName("on_surface_light")
        val onSurfaceLight: String,
        @SerializedName("surface_variant_light")
        val surfaceVariantLight: String,
        @SerializedName("on_surface_variant_light")
        val onSurfaceVariantLight: String,
        @SerializedName("outline_light")
        val outlineLight: String,
        @SerializedName("outline_variant_light")
        val outlineVariantLight: String,
        @SerializedName("background_dark")
        val backgroundDark: String,
        @SerializedName("on_background_dark")
        val onBackgroundDark: String,
        @SerializedName("surface_dark")
        val surfaceDark: String,
        @SerializedName("on_surface_dark")
        val onSurfaceDark: String,
        @SerializedName("surface_variant_dark")
        val surfaceVariantDark: String,
        @SerializedName("on_surface_variant_dark")
        val onSurfaceVariantDark: String,
        @SerializedName("outline_dark")
        val outlineDark: String,
        @SerializedName("outline_variant_dark")
        val outlineVariantDark: String
    )

    data class Typography(
        @SerializedName("primary_font_family")
        val primaryFontFamily: String,
        @SerializedName("secondary_font_family")
        val secondaryFontFamily: String
    )
}
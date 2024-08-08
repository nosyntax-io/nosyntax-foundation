package io.nosyntax.foundation.domain.model.app_config

data class Theme(
    val colorScheme: ColorScheme,
    val typography: Typography,
    val darkMode: Boolean
) {
    data class ColorScheme(
        val primary: String,
        val onPrimary: String,
        val secondary: String,
        val onSecondary: String,
        val backgroundLight: String,
        val onBackgroundLight: String,
        val surfaceLight: String,
        val onSurfaceLight: String,
        val outlineLight: String,
        val outlineVariantLight: String,
        val backgroundDark: String,
        val onBackgroundDark: String,
        val surfaceDark: String,
        val onSurfaceDark: String,
        val outlineDark: String,
        val outlineVariantDark: String
    )

    data class Typography(
        val primaryFontFamily: String,
        val secondaryFontFamily: String
    )
}
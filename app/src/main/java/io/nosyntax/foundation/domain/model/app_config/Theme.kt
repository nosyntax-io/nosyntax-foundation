package io.nosyntax.foundation.domain.model.app_config

import androidx.compose.ui.graphics.Color

data class Theme(
    val colorScheme: ColorScheme,
    val typography: Typography,
    val darkMode: Boolean
) {
    data class ColorScheme(
        val primary: Color,
        val onPrimary: Color,
        val secondary: Color,
        val onSecondary: Color,
        val backgroundLight: Color,
        val onBackgroundLight: Color,
        val surfaceLight: Color,
        val onSurfaceLight: Color,
        val surfaceVariantLight: Color,
        val onSurfaceVariantLight: Color,
        val outlineLight: Color,
        val outlineVariantLight: Color,
        val backgroundDark: Color,
        val onBackgroundDark: Color,
        val surfaceDark: Color,
        val onSurfaceDark: Color,
        val surfaceVariantDark: Color,
        val onSurfaceVariantDark: Color,
        val outlineDark: Color,
        val outlineVariantDark: Color
    )

    data class Typography(
        val primaryFontFamily: String,
        val secondaryFontFamily: String
    )
}
package io.nosyntax.foundation.presentation.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import io.nosyntax.foundation.domain.model.app_config.Theme

class ThemeProvider {
    fun resolveTheme(theme: Theme): ThemeData {
        return ThemeData(
            colorScheme = mapColorScheme(theme.colorScheme),
            typography = mapTypography(theme.typography),
            shapes = Shapes
        )
    }

    private fun mapColorScheme(colorScheme: Theme.ColorScheme): ColorScheme {
        return ColorScheme(
            primary = colorScheme.primary,
            onPrimary = colorScheme.onPrimary,
            secondary = colorScheme.secondary,
            onSecondary = colorScheme.onSecondary,
            backgroundLight = colorScheme.backgroundLight,
            onBackgroundLight = colorScheme.onBackgroundLight,
            surfaceLight = colorScheme.surfaceLight,
            onSurfaceLight = colorScheme.onSurfaceLight,
            outlineLight = colorScheme.outlineLight,
            outlineVariantLight = colorScheme.outlineVariantLight,
            backgroundDark = colorScheme.backgroundDark,
            onBackgroundDark = colorScheme.onBackgroundDark,
            surfaceDark = colorScheme.surfaceDark,
            onSurfaceDark = colorScheme.onSurfaceDark,
            outlineDark = colorScheme.outlineDark,
            outlineVariantDark = colorScheme.outlineVariantDark
        )
    }

    private fun mapTypography(typography: Theme.Typography): Typography {
        val primaryFont = FontFamily(Font(GoogleFont(typography.primaryFontFamily), googleFontProvider))
        val secondaryFont = FontFamily(Font(GoogleFont(typography.secondaryFontFamily), googleFontProvider))

        return Typography().resolveTypography(
            primaryFont = primaryFont,
            secondaryFont = secondaryFont
        )
    }
}
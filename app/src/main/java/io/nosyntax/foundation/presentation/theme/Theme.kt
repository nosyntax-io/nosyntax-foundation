package io.nosyntax.foundation.presentation.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

@Composable
fun FoundationTheme(
    colorScheme: ColorScheme = ColorScheme(),
    typography: DynamicTypography = DynamicTypography(),
    statusBarColor: String = "neutral",
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val view = LocalView.current

    val resolvedColorScheme = if (darkTheme) {
        darkColorScheme(
            primary = colorScheme.primary,
            onPrimary = colorScheme.onPrimary,
            secondary = colorScheme.secondary,
            onSecondary = colorScheme.onSecondary,
            background = colorScheme.backgroundDark,
            onBackground = colorScheme.onBackgroundDark,
            surface = colorScheme.surfaceDark,
            onSurface = colorScheme.onSurfaceDark,
            outline = colorScheme.outlineDark,
            outlineVariant = colorScheme.outlineVariantDark
        )
    } else {
        lightColorScheme(
            primary = colorScheme.primary,
            onPrimary = colorScheme.onPrimary,
            secondary = colorScheme.secondary,
            onSecondary = colorScheme.onSecondary,
            background = colorScheme.backgroundLight,
            onBackground = colorScheme.onBackgroundLight,
            surface = colorScheme.surfaceLight,
            onSurface = colorScheme.onSurfaceLight,
            outline = colorScheme.outlineLight,
            outlineVariant = colorScheme.outlineVariantLight
        )
    }

    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window

            val resolvedStatusBarColor = when (statusBarColor) {
                "neutral" -> if (darkTheme) colorScheme.surfaceDark else colorScheme.surfaceLight
                else -> colorScheme.primary
            }
            window.statusBarColor = resolvedStatusBarColor.toArgb()

            if (statusBarColor != "solid") {
                WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
            }
        }
    }

    MaterialTheme(
        colorScheme = resolvedColorScheme,
        typography = Typography.copy(
            titleMedium = Typography.titleMedium.copy(
                fontFamily = typography.primaryFontFamily
            ),
            bodyMedium = Typography.bodyMedium.copy(
                fontFamily = typography.secondaryFontFamily
            )
        ),
        shapes = Shapes,
        content = content
    )
}
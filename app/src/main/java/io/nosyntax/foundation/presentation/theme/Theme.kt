package io.nosyntax.foundation.presentation.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

data class ThemeData(
    val colorScheme: ColorScheme = ColorScheme(),
    val typography: DynamicTypography = DynamicTypography(),
    val shapes: Shapes = Shapes
)

@Composable
fun FoundationTheme(
    theme: ThemeData = ThemeData(),
    darkTheme: Boolean = isSystemInDarkTheme(),
    statusBarColor: String = "neutral",
    content: @Composable () -> Unit
) {
    val view = LocalView.current

    val colorScheme = if (darkTheme) {
        darkColorScheme(
            primary = theme.colorScheme.primary,
            onPrimary = theme.colorScheme.onPrimary,
            secondary = theme.colorScheme.secondary,
            onSecondary = theme.colorScheme.onSecondary,
            background = theme.colorScheme.backgroundDark,
            onBackground = theme.colorScheme.onBackgroundDark,
            surface = theme.colorScheme.surfaceDark,
            onSurface = theme.colorScheme.onSurfaceDark,
            outline = theme.colorScheme.outlineDark,
            outlineVariant = theme.colorScheme.outlineVariantDark
        )
    } else {
        lightColorScheme(
            primary = theme.colorScheme.primary,
            onPrimary = theme.colorScheme.onPrimary,
            secondary = theme.colorScheme.secondary,
            onSecondary = theme.colorScheme.onSecondary,
            background = theme.colorScheme.backgroundLight,
            onBackground = theme.colorScheme.onBackgroundLight,
            surface = theme.colorScheme.surfaceLight,
            onSurface = theme.colorScheme.onSurfaceLight,
            outline = theme.colorScheme.outlineLight,
            outlineVariant = theme.colorScheme.outlineVariantLight
        )
    }

    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window

            window.statusBarColor = when (statusBarColor) {
                "neutral" -> colorScheme.surface
                else -> colorScheme.primary
            }.toArgb()

            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars =
                statusBarColor == "neutral" && !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography.copy(
            titleMedium = Typography.titleMedium.copy(
                fontFamily = theme.typography.primaryFontFamily
            ),
            bodyMedium = Typography.bodyMedium.copy(
                fontFamily = theme.typography.secondaryFontFamily
            )
        ),
        shapes = theme.shapes,
        content = content
    )
}
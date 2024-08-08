package io.nosyntax.foundation.ui.theme

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
fun DynamicTheme(
    dynamicColorScheme: DynamicColorScheme = DynamicColorScheme(),
    dynamicTypography: DynamicTypography = DynamicTypography(),
    statusBarColor: String = "neutral",
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        darkColorScheme(
            primary = dynamicColorScheme.primary,
            onPrimary = dynamicColorScheme.onPrimary,
            secondary = dynamicColorScheme.secondary,
            onSecondary = dynamicColorScheme.onSecondary,
            background = dynamicColorScheme.backgroundDark,
            onBackground = dynamicColorScheme.onBackgroundDark,
            surface = dynamicColorScheme.surfaceDark,
            onSurface = dynamicColorScheme.onSurfaceDark,
            outline = dynamicColorScheme.outlineDark,
            outlineVariant = dynamicColorScheme.outlineVariantDark
        )
    } else {
        lightColorScheme(
            primary = dynamicColorScheme.primary,
            onPrimary = dynamicColorScheme.onPrimary,
            secondary = dynamicColorScheme.secondary,
            onSecondary = dynamicColorScheme.onSecondary,
            background = dynamicColorScheme.backgroundLight,
            onBackground = dynamicColorScheme.onBackgroundLight,
            surface = dynamicColorScheme.surfaceLight,
            onSurface = dynamicColorScheme.onSurfaceLight,
            outline = dynamicColorScheme.outlineLight,
            outlineVariant = dynamicColorScheme.outlineVariantLight
        )
    }

    val view = LocalView.current

    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window

            val statusBarColorToSet = when (statusBarColor) {
                "neutral" -> if (darkTheme) dynamicColorScheme.surfaceDark else dynamicColorScheme.surfaceLight
                else -> dynamicColorScheme.primary
            }
            window.statusBarColor = statusBarColorToSet.toArgb()

            if (statusBarColor != "solid") {
                WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
            }
        }
    }
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography.copy(
            titleMedium = Typography.titleMedium.copy(
                fontFamily = dynamicTypography.primaryFontFamily
            ),
            bodyMedium = Typography.bodyMedium.copy(
                fontFamily = dynamicTypography.secondaryFontFamily
            )
        ),
        shapes = Shapes,
        content = content
    )
}
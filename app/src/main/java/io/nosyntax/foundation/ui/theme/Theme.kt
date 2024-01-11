package io.nosyntax.foundation.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val lightColorScheme = lightColorScheme(
    primary = ColorPrimary,
    primaryContainer = ColorPrimaryVariant,
    secondary = ColorSecondary,
    onPrimaryContainer = Color.Transparent,
    background = ColorBackgroundLight,
    onBackground = ColorOnBackgroundLight,
    surface = ColorSurfaceLight,
    onSurface = ColorOnSurfaceLight
)

private val darkColorScheme = darkColorScheme(
    primary = ColorPrimary,
    primaryContainer = ColorPrimaryVariant,
    secondary = ColorSecondary,
    onPrimaryContainer = Color.Transparent,
    background = ColorBackgroundDark,
    onBackground = ColorOnBackgroundDark,
    surface = ColorSurfaceDark,
    onSurface = ColorOnSurfaceDark
)

@Composable
fun DynamicTheme(
    dynamicColorScheme: DynamicColorScheme = DynamicColorScheme(),
    dynamicTypography: DynamicTypography = DynamicTypography(),
    statusBarColor: String = "neutral",
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> darkColorScheme
        else -> lightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window

            if (window != null) {
                val statusBarColorToSet = if (statusBarColor == "neutral") {
                    if (darkTheme) dynamicColorScheme.colorSurfaceDark else dynamicColorScheme.colorSurfaceLight
                } else {
                    dynamicColorScheme.colorPrimary
                }
                window.statusBarColor = statusBarColorToSet.toArgb()

                if (statusBarColor != "solid") {
                    WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
                }
            }
        }
    }
    MaterialTheme(
        colorScheme = colorScheme.copy(
            primary = dynamicColorScheme.colorPrimary,
            primaryContainer = dynamicColorScheme.colorPrimaryVariant,
            secondary = dynamicColorScheme.colorSecondary,
            secondaryContainer = dynamicColorScheme.colorSecondaryVariant,
            background = if (darkTheme) dynamicColorScheme.colorBackgroundDark else dynamicColorScheme.colorBackgroundLight,
            onBackground = if (darkTheme) dynamicColorScheme.colorOnBackgroundDark else dynamicColorScheme.colorOnBackgroundLight,
            surface = if (darkTheme) dynamicColorScheme.colorSurfaceDark else dynamicColorScheme.colorSurfaceLight,
            onSurface = if (darkTheme) dynamicColorScheme.colorOnSurfaceDark else dynamicColorScheme.colorOnSurfaceLight
        ),
        typography = Typography.run {
            copy(
                titleMedium = titleMedium.copy(
                    fontFamily = dynamicTypography.primaryFontFamily
                ),
                bodyMedium = bodyMedium.copy(
                    fontFamily = dynamicTypography.secondaryFontFamily
                )
            )
        },
        shapes = Shapes,
        content = content
    )
}
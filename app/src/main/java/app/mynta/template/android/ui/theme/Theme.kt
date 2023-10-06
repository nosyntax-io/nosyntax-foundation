package app.mynta.template.android.ui.theme

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
    primaryContainer = ColorPrimaryContainer,
    secondary = ColorSecondary,
    onPrimaryContainer = Color.Transparent,
    background = ColorBackgroundLight,
    onBackground = ColorOnBackgroundLight,
    surface = ColorSurfaceLight,
    onSurface = ColorOnSurfaceLight
)

private val darkColorScheme = darkColorScheme(
    primary = ColorPrimary,
    primaryContainer = ColorPrimaryContainer,
    secondary = ColorSecondary,
    onPrimaryContainer = Color.Transparent,
    background = ColorBackgroundDark,
    onBackground = ColorOnBackgroundDark,
    surface = ColorSurfaceDark,
    onSurface = ColorOnSurfaceDark
)

@Composable
fun DynamicTheme(
    dynamicThemeColors: DynamicThemeColors = DynamicThemeColors(),
    dynamicTypography: DynamicTypography = DynamicTypography(),
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
            window.statusBarColor = dynamicThemeColors.colorPrimary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }
    MaterialTheme(
        colorScheme = colorScheme.copy(
            primary = dynamicThemeColors.colorPrimary,
            primaryContainer = dynamicThemeColors.colorPrimaryContainer,
            secondary = dynamicThemeColors.colorSecondary,
            secondaryContainer = dynamicThemeColors.colorSecondaryContainer,
            background = if (darkTheme) dynamicThemeColors.colorBackgroundDark else dynamicThemeColors.colorBackgroundLight,
            onBackground = if (darkTheme) dynamicThemeColors.colorOnBackgroundDark else dynamicThemeColors.colorOnBackgroundLight,
            surface = if (darkTheme) dynamicThemeColors.colorSurfaceDark else dynamicThemeColors.colorSurfaceLight,
            onSurface = if (darkTheme) dynamicThemeColors.colorOnSurfaceDark else dynamicThemeColors.colorOnSurfaceLight
        ),
        typography = Typography.run {
            copy(
                titleMedium = titleMedium.copy(
                    fontFamily = dynamicTypography.headingTypeface
                ),
                bodyMedium = bodyMedium.copy(
                    fontFamily = dynamicTypography.bodyTypeface
                )
            )
        },
        shapes = Shapes,
        content = content
    )
}
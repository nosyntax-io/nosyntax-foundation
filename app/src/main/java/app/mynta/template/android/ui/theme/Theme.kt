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

data class DynamicThemeColors(
    val primaryColor: Color = ColorPrimary,
    val secondaryColor: Color = ColorSecondary,
    val primaryContainer: Color = ColorPrimaryContainer
)

private val lightColorScheme = lightColorScheme(
    primary = ColorPrimary,
    primaryContainer = ColorPrimaryContainer,
    secondary = ColorSecondary,
    onPrimaryContainer = Color.Transparent,
    background = ColorBackgroundLight,
    onBackground = ColorOnBackgroundLight,
    surface = ColorSurfaceLight,
    surfaceVariant = ColorSurfaceVariantLight,
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
    surfaceVariant = ColorSurfaceVariantDark,
    onSurface = ColorOnSurfaceDark
)

@Composable
fun DynamicTheme(
    dynamicColors: DynamicThemeColors = DynamicThemeColors(),
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
            window.statusBarColor = dynamicColors.primaryContainer.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }
    MaterialTheme(
        colorScheme = colorScheme.copy(
            primary = dynamicColors.primaryColor,
            secondary = dynamicColors.secondaryColor,
            primaryContainer = dynamicColors.primaryContainer
        ),
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}
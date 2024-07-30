package io.nosyntax.foundation.ui.theme

import androidx.compose.ui.graphics.Color

data class DynamicColorScheme(
    val primary: Color = ColorPrimary,
    val onPrimary: Color = ColorOnPrimary,
    val secondary: Color = ColorSecondary,
    val onSecondary: Color = ColorOnSecondary,
    val backgroundLight: Color = ColorBackgroundLight,
    val onBackgroundLight: Color = ColorOnBackgroundLight,
    val surfaceLight: Color = ColorSurfaceLight,
    val onSurfaceLight: Color = ColorOnSurfaceLight,
    val backgroundDark: Color = ColorBackgroundDark,
    val onBackgroundDark: Color = ColorOnBackgroundDark,
    val surfaceDark: Color = ColorSurfaceDark,
    val onSurfaceDark: Color = ColorOnSurfaceDark
)

val ColorPrimary = Color(0xFF653ff5)
val ColorOnPrimary = Color(0xFFFFFFFF)
val ColorSecondary = Color(0xFF121212)
val ColorOnSecondary = Color(0xFFFFFFFF)
val ColorBackgroundLight = Color(0xFFF5F5F5)
val ColorOnBackgroundLight = Color(0xFF140F1F)
val ColorSurfaceLight = Color(0xFFECEDF1)
val ColorOnSurfaceLight = Color(0xFF212121)
val ColorBackgroundDark = Color(0xFF181A20)
val ColorOnBackgroundDark = Color(0xFFFFFFFF)
val ColorSurfaceDark = Color(0xFF21252F)
val ColorOnSurfaceDark = Color(0xFFF0F0F0)
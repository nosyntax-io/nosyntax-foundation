package io.nosyntax.foundation.presentation.theme

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
    val surfaceVariantLight: Color = ColorSurfaceVariantLight,
    val onSurfaceVariantLight: Color = ColorOnSurfaceVariantLight,
    val outlineLight: Color = ColorOutlineLight,
    val outlineVariantLight: Color = ColorOutlineVariantLight,
    val backgroundDark: Color = ColorBackgroundDark,
    val onBackgroundDark: Color = ColorOnBackgroundDark,
    val surfaceDark: Color = ColorSurfaceDark,
    val onSurfaceDark: Color = ColorOnSurfaceDark,
    val surfaceVariantDark: Color = ColorSurfaceVariantDark,
    val onSurfaceVariantDark: Color = ColorOnSurfaceVariantDark,
    val outlineDark: Color = ColorOutlineDark,
    val outlineVariantDark: Color = ColorOutlineVariantDark
)

val ColorPrimary = Color(0xFF369770)
val ColorOnPrimary = Color(0xFFFFFFFF)
val ColorSecondary = Color(0xFF369770)
val ColorOnSecondary = Color(0xFFFFFFFF)
val ColorBackgroundLight = Color(0xFFfcfcfc)
val ColorOnBackgroundLight = Color(0xFF19191a)
val ColorSurfaceLight = Color(0xFFf4f4f4)
val ColorOnSurfaceLight = Color(0xFF19191a)
val ColorSurfaceVariantLight = Color(0xFFf4f4f4)
val ColorOnSurfaceVariantLight = Color(0xFF19191a)
val ColorOutlineLight = Color(0xFFf4f4f4)
val ColorOutlineVariantLight = Color(0xFFf4f4f4)
val ColorBackgroundDark = Color(0xFF181A20)
val ColorOnBackgroundDark = Color(0xFFFFFFFF)
val ColorSurfaceDark = Color(0xFF21252F)
val ColorOnSurfaceDark = Color(0xFFF0F0F0)
val ColorSurfaceVariantDark = Color(0xFFf4f4f4)
val ColorOnSurfaceVariantDark = Color(0xFF19191a)
val ColorOutlineDark = Color(0xFF2C2F36)
val ColorOutlineVariantDark = Color(0xFFf4f4f4)
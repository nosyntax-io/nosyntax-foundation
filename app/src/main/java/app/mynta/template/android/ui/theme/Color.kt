package app.mynta.template.android.ui.theme

import androidx.compose.ui.graphics.Color

data class DynamicThemeColors(
    val colorPrimary: Color = ColorPrimary,
    val colorPrimaryContainer: Color = ColorPrimaryContainer,
    val colorSecondary: Color = ColorSecondary,
    val colorSecondaryContainer: Color = ColorSecondaryContainer,
    val colorBackgroundLight: Color = ColorBackgroundLight,
    val colorOnBackgroundLight: Color = ColorOnBackgroundLight,
    val colorSurfaceLight: Color = ColorSurfaceLight,
    val colorOnSurfaceLight: Color = ColorOnSurfaceLight,
    val colorBackgroundDark: Color = ColorBackgroundDark,
    val colorOnBackgroundDark: Color = ColorOnBackgroundDark,
    val colorSurfaceDark: Color = ColorSurfaceDark,
    val colorOnSurfaceDark: Color = ColorOnSurfaceDark
)

val ColorPrimary = Color(0xFF8EBBFF)
val ColorPrimaryContainer = Color(0x338EBBFF)
val ColorSecondary = Color(0xFF121212)
val ColorSecondaryContainer = Color(0xFF121212)

val ColorBackgroundLight = Color(0xFFFFFFFF)
val ColorOnBackgroundLight = Color(0xFF140F1F)
val ColorSurfaceLight = Color(0xFFF5F6FA)
val ColorSurfaceVariantLight = Color(0xFFE0E3EF)
val ColorOnSurfaceLight = Color(0xFF212121)
val ColorInverseOnSurfaceLight = Color(0xFFC7C7C7)

val ColorBackgroundDark = Color(0xFF181A20)
val ColorOnBackgroundDark = Color(0xFFFFFFFF)
val ColorSurfaceDark = Color(0xFF21252F)
val ColorSurfaceVariantDark = Color(0xFF5B6E77)
val ColorOnSurfaceDark = Color(0xFFF0F0F0)
val ColorInverseOnSurfaceDark = Color(0xFF5B6E77)
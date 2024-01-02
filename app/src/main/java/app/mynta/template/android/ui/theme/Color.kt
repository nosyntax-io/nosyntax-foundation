package app.mynta.template.android.ui.theme

import androidx.compose.ui.graphics.Color

data class DynamicColorScheme(
    val colorPrimary: Color = ColorPrimary,
    val colorPrimaryVariant: Color = ColorPrimaryVariant,
    val colorSecondary: Color = ColorSecondary,
    val colorSecondaryVariant: Color = ColorSecondaryVariant,
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
val ColorPrimaryVariant = Color(0x338EBBFF)
val ColorSecondary = Color(0xFF121212)
val ColorSecondaryVariant = Color(0xFF121212)
val ColorBackgroundLight = Color(0xFFF5F5F5)
val ColorOnBackgroundLight = Color(0xFF140F1F)
val ColorSurfaceLight = Color(0xFFECEDF1)
val ColorOnSurfaceLight = Color(0xFF212121)
val ColorBackgroundDark = Color(0xFF181A20)
val ColorOnBackgroundDark = Color(0xFFFFFFFF)
val ColorSurfaceDark = Color(0xFF21252F)
val ColorOnSurfaceDark = Color(0xFFF0F0F0)
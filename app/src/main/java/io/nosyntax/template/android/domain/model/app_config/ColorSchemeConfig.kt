package io.nosyntax.template.android.domain.model.app_config

data class ColorSchemeConfig(
    val primary: String,
    val primaryVariant: String,
    val secondary: String,
    val secondaryVariant: String,
    val backgroundLight: String,
    val onBackgroundLight: String,
    val surfaceLight: String,
    val onSurfaceLight: String,
    val backgroundDark: String,
    val onBackgroundDark: String,
    val surfaceDark: String,
    val onSurfaceDark: String
)
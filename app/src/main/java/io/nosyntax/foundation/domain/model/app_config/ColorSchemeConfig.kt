package io.nosyntax.foundation.domain.model.app_config

data class ColorSchemeConfig(
    val primary: String,
    val onPrimary: String,
    val secondary: String,
    val onSecondary: String,
    val backgroundLight: String,
    val onBackgroundLight: String,
    val surfaceLight: String,
    val onSurfaceLight: String,
    val backgroundDark: String,
    val onBackgroundDark: String,
    val surfaceDark: String,
    val onSurfaceDark: String
)
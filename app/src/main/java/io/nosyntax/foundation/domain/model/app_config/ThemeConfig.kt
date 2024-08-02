package io.nosyntax.foundation.domain.model.app_config

data class ThemeConfig(
    val colorScheme: ColorSchemeConfig,
    val typography: TypographyConfig,
    val darkMode: Boolean
)
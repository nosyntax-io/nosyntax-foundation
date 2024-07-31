package io.nosyntax.foundation.data.mapper

import io.nosyntax.foundation.data.source.remote.dto.app_config.AppBarConfigDto
import io.nosyntax.foundation.data.source.remote.dto.app_config.ThemeConfigDto
import io.nosyntax.foundation.data.source.remote.dto.app_config.ComponentsConfigDto
import io.nosyntax.foundation.data.source.remote.dto.app_config.AppConfigDto
import io.nosyntax.foundation.data.source.remote.dto.app_config.LoadingIndicatorConfigDto
import io.nosyntax.foundation.data.source.remote.dto.app_config.MonetizationConfigDto
import io.nosyntax.foundation.data.source.remote.dto.app_config.SideMenuConfigDto
import io.nosyntax.foundation.data.source.remote.dto.app_config.ColorSchemeConfigDto
import io.nosyntax.foundation.data.source.remote.dto.app_config.ConfigurationDto
import io.nosyntax.foundation.data.source.remote.dto.app_config.NavigationConfigDto
import io.nosyntax.foundation.data.source.remote.dto.app_config.TypographyConfigDto
import io.nosyntax.foundation.domain.model.NavigationItem
import io.nosyntax.foundation.domain.model.app_config.AppBarConfig
import io.nosyntax.foundation.domain.model.app_config.ThemeConfig
import io.nosyntax.foundation.domain.model.app_config.ComponentsConfig
import io.nosyntax.foundation.domain.model.app_config.AppConfig
import io.nosyntax.foundation.domain.model.app_config.LoadingIndicatorConfig
import io.nosyntax.foundation.domain.model.app_config.MonetizationConfig
import io.nosyntax.foundation.domain.model.app_config.TypographyConfig
import io.nosyntax.foundation.domain.model.app_config.SideMenuConfig
import io.nosyntax.foundation.domain.model.app_config.ColorSchemeConfig
import io.nosyntax.foundation.domain.model.app_config.NavigationConfig

fun AppConfigDto.toConfiguration(): AppConfig {
    val (app) = this

    return AppConfig(
        app = app.toApp(),
    )
}

fun AppConfigDto.App.toApp(): AppConfig.App {
    return AppConfig.App(
        id = this.id,
        name = this.name,
        category = this.category,
        description = this.description,
        theme = this.theme.toTheme(),
        components = this.components.toComponents(),
        configuration = this.configuration.toConfiguration(),
        navigation = navigation.toNavigation()
    )
}

fun ConfigurationDto.toConfiguration(): AppConfig.Configuration {
    val (monetization) = this

    return AppConfig.Configuration(
        monetization = monetization.toMonetization()
    )
}

fun ThemeConfigDto.toTheme(): ThemeConfig {
    val (colorScheme, typography, settings) = this

    return ThemeConfig(
        colorScheme = colorScheme.toColorScheme(),
        typography = typography.toTypography(),
        settings = settings.toSettings()
    )
}

fun ColorSchemeConfigDto.toColorScheme(): ColorSchemeConfig {
    return ColorSchemeConfig(
        primary = this.primary,
        onPrimary = this.onPrimary,
        secondary = this.secondary,
        onSecondary = this.onSecondary,
        backgroundLight = this.backgroundLight,
        onBackgroundLight = this.onBackgroundLight,
        surfaceLight = this.surfaceLight,
        onSurfaceLight = this.onSurfaceLight,
        outlineLight = this.outlineLight,
        backgroundDark = this.backgroundDark,
        onBackgroundDark = this.onBackgroundDark,
        surfaceDark = this.surfaceDark,
        onSurfaceDark = this.onSurfaceDark,
        outlineDark = this.outlineDark
    )
}

fun TypographyConfigDto.toTypography(): TypographyConfig {
    return TypographyConfig(
        primaryFontFamily = this.primaryFontFamily,
        secondaryFontFamily = this.secondaryFontFamily
    )
}

fun ThemeConfigDto.SettingsConfigDto.toSettings(): ThemeConfig.SettingsConfig {
    return ThemeConfig.SettingsConfig(
        darkMode = this.darkMode
    )
}

fun ComponentsConfigDto.toComponents(): ComponentsConfig {
    val (appBar, sideMenu, loadingIndicator) = this

    return ComponentsConfig(
        appBar = appBar.toAppBar(),
        sideMenu = sideMenu.toSideMenu(),
        loadingIndicator = loadingIndicator.toLoadingIndicator()
    )
}

fun AppBarConfigDto.toAppBar(): AppBarConfig {
    return AppBarConfig(
        visibile = this.visible,
        background = this.background,
        title = AppBarConfig.Title(
            visible = this.title.visible,
            alignment = this.title.alignment
        )
    )
}

fun SideMenuConfigDto.toSideMenu(): SideMenuConfig {
    return SideMenuConfig(
        display = this.visible,
        background = this.background,
        header = SideMenuConfig.Header(
            display = this.header.visible,
            image = this.header.image
        )
    )
}

fun LoadingIndicatorConfigDto.toLoadingIndicator(): LoadingIndicatorConfig {
    return LoadingIndicatorConfig(
        display = this.visible,
        animation = this.animation,
        background = this.background,
        color = this.color
    )
}

fun MonetizationConfigDto.toMonetization(): MonetizationConfig {
    return MonetizationConfig(
        ads = MonetizationConfig.Ads(
            enabled = this.ads.enabled,
            bannerDisplay = this.ads.bannerDisplay,
            interstitialDisplay = this.ads.interstitialDisplay
        )
    )
}

fun NavigationConfigDto.toNavigation(): NavigationConfig {
    return NavigationConfig(
        default = this.default,
        items = this.items.map { item ->
            NavigationItem(
                id = item.id,
                type = item.type,
                label = item.label,
                icon = item.icon,
                url = item.url
            )
        }
    )
}
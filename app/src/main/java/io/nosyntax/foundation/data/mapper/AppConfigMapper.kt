package io.nosyntax.foundation.data.mapper

import io.nosyntax.foundation.data.source.remote.dto.app_config.AppBarConfigDto
import io.nosyntax.foundation.data.source.remote.dto.app_config.ThemeConfigDto
import io.nosyntax.foundation.data.source.remote.dto.app_config.BottomBarConfigDto
import io.nosyntax.foundation.data.source.remote.dto.app_config.ComponentsConfigDto
import io.nosyntax.foundation.data.source.remote.dto.app_config.AppConfigDto
import io.nosyntax.foundation.data.source.remote.dto.app_config.LoadingIndicatorConfigDto
import io.nosyntax.foundation.data.source.remote.dto.app_config.ModulesConfigDto
import io.nosyntax.foundation.data.source.remote.dto.app_config.MonetizationConfigDto
import io.nosyntax.foundation.data.source.remote.dto.app_config.SideMenuConfigDto
import io.nosyntax.foundation.data.source.remote.dto.app_config.ColorSchemeConfigDto
import io.nosyntax.foundation.data.source.remote.dto.app_config.ConfigurationDto
import io.nosyntax.foundation.data.source.remote.dto.app_config.NavigationConfigDto
import io.nosyntax.foundation.data.source.remote.dto.app_config.TypographyConfigDto
import io.nosyntax.foundation.domain.model.NavigationItem
import io.nosyntax.foundation.domain.model.app_config.AppBarConfig
import io.nosyntax.foundation.domain.model.app_config.ThemeConfig
import io.nosyntax.foundation.domain.model.app_config.BottomBarConfig
import io.nosyntax.foundation.domain.model.app_config.ComponentsConfig
import io.nosyntax.foundation.domain.model.app_config.AppConfig
import io.nosyntax.foundation.domain.model.app_config.LoadingIndicatorConfig
import io.nosyntax.foundation.domain.model.app_config.ModulesConfig
import io.nosyntax.foundation.domain.model.app_config.MonetizationConfig
import io.nosyntax.foundation.domain.model.app_config.TypographyConfig
import io.nosyntax.foundation.domain.model.app_config.SideMenuConfig
import io.nosyntax.foundation.domain.model.app_config.ColorSchemeConfig
import io.nosyntax.foundation.domain.model.app_config.NavigationConfig
import io.nosyntax.foundation.domain.model.app_config.WebKitConfig

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
        configuration = this.configuration.toConfiguration(),
    )
}

fun ConfigurationDto.toConfiguration(): AppConfig.Configuration {
    val (theme, components, monetization, modules, navigation) = this

    return AppConfig.Configuration(
        theme = theme.toTheme(),
        components = components.toComponents(),
        monetization = monetization.toMonetization(),
        modules = modules.toModules(),
        navigation = navigation.toNavigation()
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
        backgroundDark = this.backgroundDark,
        onBackgroundDark = this.onBackgroundDark,
        surfaceDark = this.surfaceDark,
        onSurfaceDark = this.onSurfaceDark
    )
}

fun TypographyConfigDto.toTypography(): TypographyConfig {
    return TypographyConfig(
        primaryFontFamily = this.primaryFontFamily,
        secondaryFontFamily = this.secondaryFontFamily
    )
}

fun ThemeConfigDto.SettingsConfigDto.toSettings(): ThemeConfig.SettingsConfig {
    return io.nosyntax.foundation.domain.model.app_config.ThemeConfig.SettingsConfig(
        darkMode = this.darkMode
    )
}

fun ComponentsConfigDto.toComponents(): ComponentsConfig {
    val (appBar, sideMenu, bottomBar, loadingIndicator) = this

    return ComponentsConfig(
        appBar = appBar.toAppBar(),
        sideMenu = sideMenu.toSideMenu(),
        bottomBar = bottomBar.toBottomBar(),
        loadingIndicator = loadingIndicator.toLoadingIndicator()
    )
}

fun AppBarConfigDto.toAppBar(): AppBarConfig {
    return AppBarConfig(
        display = this.display,
        background = this.background,
        title = io.nosyntax.foundation.domain.model.app_config.AppBarConfig.Title(
            display = this.title.display,
            position = this.title.position
        )
    )
}

fun SideMenuConfigDto.toSideMenu(): SideMenuConfig {
    return SideMenuConfig(
        display = this.display,
        background = this.background,
        header = io.nosyntax.foundation.domain.model.app_config.SideMenuConfig.Header(
            display = this.header.display,
            image = this.header.image
        )
    )
}

fun BottomBarConfigDto.toBottomBar(): BottomBarConfig {
    return BottomBarConfig(
        display = this.display,
        background = this.background,
        label = this.label
    )
}

fun LoadingIndicatorConfigDto.toLoadingIndicator(): LoadingIndicatorConfig {
    return LoadingIndicatorConfig(
        display = this.display,
        animation = this.animation,
        background = this.background,
        color = this.color
    )
}

fun MonetizationConfigDto.toMonetization(): MonetizationConfig {
    return MonetizationConfig(
        ads = io.nosyntax.foundation.domain.model.app_config.MonetizationConfig.Ads(
            enabled = this.ads.enabled,
            bannerDisplay = this.ads.bannerDisplay,
            interstitialDisplay = this.ads.interstitialDisplay
        )
    )
}

fun ModulesConfigDto.toModules(): ModulesConfig {
    return ModulesConfig(
        webkit = WebKitConfig(
            userAgent = WebKitConfig.UserAgent(
                android = this.webkit.userAgent.android
            ),
            customCss = this.webkit.customCss
        )
    )
}

fun NavigationConfigDto.toNavigation(): NavigationConfig {
    return NavigationConfig(
        default = this.default,
        items = this.items.map { item ->
            NavigationItem(
                route = item.route,
                label = item.label,
                icon = item.icon,
                deeplink = item.deeplink
            )
        }
    )
}
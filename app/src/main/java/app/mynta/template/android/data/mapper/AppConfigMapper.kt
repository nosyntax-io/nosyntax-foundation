package app.mynta.template.android.data.mapper

import app.mynta.template.android.data.source.remote.dto.app_config.AppBarConfigDto
import app.mynta.template.android.data.source.remote.dto.app_config.ThemeConfigDto
import app.mynta.template.android.data.source.remote.dto.app_config.BottomBarConfigDto
import app.mynta.template.android.data.source.remote.dto.app_config.ComponentsConfigDto
import app.mynta.template.android.data.source.remote.dto.app_config.AppConfigDto
import app.mynta.template.android.data.source.remote.dto.app_config.LoadingIndicatorConfigDto
import app.mynta.template.android.data.source.remote.dto.app_config.ModulesConfigDto
import app.mynta.template.android.data.source.remote.dto.app_config.MonetizationConfigDto
import app.mynta.template.android.data.source.remote.dto.app_config.SideMenuConfigDto
import app.mynta.template.android.data.source.remote.dto.app_config.ColorSchemeConfigDto
import app.mynta.template.android.data.source.remote.dto.app_config.ConfigurationDto
import app.mynta.template.android.data.source.remote.dto.app_config.TypographyConfigDto
import app.mynta.template.android.domain.model.NavigationItem
import app.mynta.template.android.domain.model.app_config.AppBarConfig
import app.mynta.template.android.domain.model.app_config.ThemeConfig
import app.mynta.template.android.domain.model.app_config.BottomBarConfig
import app.mynta.template.android.domain.model.app_config.ComponentsConfig
import app.mynta.template.android.domain.model.app_config.AppConfig
import app.mynta.template.android.domain.model.app_config.LoadingIndicatorConfig
import app.mynta.template.android.domain.model.app_config.ModulesConfig
import app.mynta.template.android.domain.model.app_config.MonetizationConfig
import app.mynta.template.android.domain.model.app_config.TypographyConfig
import app.mynta.template.android.domain.model.app_config.SideMenuConfig
import app.mynta.template.android.domain.model.app_config.ColorSchemeConfig
import app.mynta.template.android.domain.model.app_config.WebKitConfig

fun AppConfigDto.toConfiguration(): AppConfig {
    val (app, configuration) = this

    return AppConfig(
        app = app.toApp(),
        configuration = configuration.toConfiguration()
    )
}

fun AppConfigDto.App.toApp(): AppConfig.App {
    return AppConfig.App(
        id = this.id,
        name = this.name,
        category = this.category,
        description = this.description
    )
}

fun ConfigurationDto.toConfiguration(): AppConfig.Configuration {
    val (theme, components, monetization, modules) = this

    return AppConfig.Configuration(
        theme = theme.toTheme(),
        components = components.toComponents(),
        monetization = monetization.toMonetization(),
        modules = modules.toModules()
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
        primaryContainer = this.primaryContainer,
        secondary = this.secondary,
        secondaryContainer = this.secondaryContainer,
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
    return ThemeConfig.SettingsConfig(
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
        title = AppBarConfig.Title(
            display = this.title.display,
            position = this.title.position
        )
    )
}

fun SideMenuConfigDto.toSideMenu(): SideMenuConfig {
    return SideMenuConfig(
        display = this.display,
        background = this.background,
        header = SideMenuConfig.Header(
            display = this.header.display,
            image = this.header.image
        ),
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
        ads = MonetizationConfig.Ads(
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
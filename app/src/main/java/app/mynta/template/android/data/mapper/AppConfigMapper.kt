package app.mynta.template.android.data.mapper

import app.mynta.template.android.data.source.remote.dto.app_config.AboutPageConfigDto
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
import app.mynta.template.android.data.source.remote.dto.app_config.TypographyConfigDto
import app.mynta.template.android.domain.model.NavigationItem
import app.mynta.template.android.domain.model.app_config.AppBarConfig
import app.mynta.template.android.domain.model.app_config.ThemeConfig
import app.mynta.template.android.domain.model.app_config.BottomBarConfig
import app.mynta.template.android.domain.model.app_config.ComponentsConfig
import app.mynta.template.android.domain.model.app_config.AppConfig
import app.mynta.template.android.domain.model.app_config.AboutPageConfig
import app.mynta.template.android.domain.model.app_config.LoadingIndicatorConfig
import app.mynta.template.android.domain.model.app_config.ModulesConfig
import app.mynta.template.android.domain.model.app_config.MonetizationConfig
import app.mynta.template.android.domain.model.app_config.TypographyConfig
import app.mynta.template.android.domain.model.app_config.SideMenuConfig
import app.mynta.template.android.domain.model.app_config.ColorSchemeConfig
import app.mynta.template.android.domain.model.app_config.WebKitConfig

fun AppConfigDto.toConfiguration(): AppConfig {
    val configuration = app.configuration
    return AppConfig(
        appId = this.app.appId,
        theme = configuration.theme.toTheme(),
        components = configuration.components.toComponents(),
        monetization = configuration.monetization.toMonetization(),
        modules = configuration.modules.toModules(),
        aboutPage = configuration.aboutPage.toAboutPage()
    )
}

fun ThemeConfigDto.toTheme(): ThemeConfig {
    val (colorScheme, typography) = this

    return ThemeConfig(
        colorScheme = colorScheme.toColorScheme(),
        typography = typography.toTypography()
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
        headingTypeface = this.headingTypeface,
        bodyTypeface = this.bodyTypeface
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

fun AboutPageConfigDto.toAboutPage(): AboutPageConfig {
    return AboutPageConfig(
        introduction = this.introduction,
        connectItems = this.connectItems.toConnectItems()
    )
}

fun List<AboutPageConfigDto.ConnectItem>.toConnectItems(): List<AboutPageConfig.ConnectItem> {
    return map { link ->
        AboutPageConfig.ConnectItem(
            label = link.label,
            icon = link.icon,
            url = link.url
        )
    }
}
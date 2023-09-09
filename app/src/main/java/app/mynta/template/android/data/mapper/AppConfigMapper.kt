package app.mynta.template.android.data.mapper

import app.mynta.template.android.data.source.remote.dto.app_config.AboutPageConfigDto
import app.mynta.template.android.data.source.remote.dto.app_config.AppBarConfigDto
import app.mynta.template.android.data.source.remote.dto.app_config.AppearanceConfigDto
import app.mynta.template.android.data.source.remote.dto.app_config.BottomBarConfigDto
import app.mynta.template.android.data.source.remote.dto.app_config.ComponentsConfigDto
import app.mynta.template.android.data.source.remote.dto.app_config.AppConfigDto
import app.mynta.template.android.data.source.remote.dto.app_config.LoadingIndicatorConfigDto
import app.mynta.template.android.data.source.remote.dto.app_config.ModulesConfigDto
import app.mynta.template.android.data.source.remote.dto.app_config.NavigationConfigDto
import app.mynta.template.android.data.source.remote.dto.app_config.SideMenuConfigDto
import app.mynta.template.android.data.source.remote.dto.app_config.ThemeColorsConfigDto
import app.mynta.template.android.data.source.remote.dto.app_config.TypographyConfigDto
import app.mynta.template.android.domain.model.app_config.AppBarConfig
import app.mynta.template.android.domain.model.app_config.AppearanceConfig
import app.mynta.template.android.domain.model.app_config.BottomBarConfig
import app.mynta.template.android.domain.model.app_config.ComponentsConfig
import app.mynta.template.android.domain.model.app_config.AppConfig
import app.mynta.template.android.domain.model.app_config.NavigationConfig
import app.mynta.template.android.domain.model.NavigationItem
import app.mynta.template.android.domain.model.app_config.AboutPageConfig
import app.mynta.template.android.domain.model.app_config.LoadingIndicatorConfig
import app.mynta.template.android.domain.model.app_config.ModulesConfig
import app.mynta.template.android.domain.model.app_config.TypographyConfig
import app.mynta.template.android.domain.model.app_config.SideMenuConfig
import app.mynta.template.android.domain.model.app_config.ThemeColorsConfig
import app.mynta.template.android.domain.model.app_config.WebKitConfig

fun AppConfigDto.toConfiguration(): AppConfig {
    val configuration = app.configuration
    return AppConfig(
        appId = this.app.appId,
        appearance = configuration.appearance.toAppearance(),
        navigation = configuration.navigation.toNavigation(),
        modules = configuration.modules.toModules(),
        aboutPage = configuration.aboutPage.toAboutPage()
    )
}

fun AppearanceConfigDto.toAppearance(): AppearanceConfig {
    val (themeColors, typography, components) = this

    return AppearanceConfig(
        themeColors = themeColors.toThemeColors(),
        typography = typography.toTypography(),
        components = components.toComponents()
    )
}

fun ThemeColorsConfigDto.toThemeColors(): ThemeColorsConfig {
    return ThemeColorsConfig(
        primary = this.primary,
        secondary = this.secondary,
        highlight = this.highlight
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

fun NavigationConfigDto.toNavigation(): NavigationConfig {
    return NavigationConfig(
        default = this.default,
        items = this.items.toNavigationItems()
    )
}

fun List<NavigationConfigDto.NavigationItem>.toNavigationItems(): List<NavigationItem> {
    return map { item ->
        NavigationItem(
            route = item.route,
            role = item.role,
            label = item.label,
            icon = item.icon,
            type = item.type,
            module = item.module?.let {
                NavigationItem.Module(
                    deeplink = it.deeplink
                )
            }
        )
    }
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
        socialLinks = this.socialLinks.toSocialLinks()
    )
}

fun List<AboutPageConfigDto.SocialLink>.toSocialLinks(): List<AboutPageConfig.SocialLink> {
    return map { link ->
        AboutPageConfig.SocialLink(
            label = link.label,
            icon = link.icon,
            url = link.url
        )
    }
}
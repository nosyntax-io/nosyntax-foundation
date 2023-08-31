package app.mynta.template.android.data.mapper

import app.mynta.template.android.data.source.remote.dto.ConfigurationDto
import app.mynta.template.android.domain.model.configuration.AppBar
import app.mynta.template.android.domain.model.configuration.Appearance
import app.mynta.template.android.domain.model.configuration.BottomNavigation
import app.mynta.template.android.domain.model.configuration.Configuration
import app.mynta.template.android.domain.model.configuration.Typography
import app.mynta.template.android.domain.model.configuration.Navigation
import app.mynta.template.android.domain.model.configuration.NavigationItem
import app.mynta.template.android.domain.model.configuration.ThemeColors

fun ConfigurationDto.toConfiguration(): Configuration {
    val configuration = app.configuration
    val navigation = configuration.navigation
    val appearance = configuration.appearance

    return Configuration(
        appId = app.appId,
        appearance = Appearance(
            themeColors = ThemeColors(
                primary = appearance.themeColors.primary,
                secondary = appearance.themeColors.secondary,
                highlight = appearance.themeColors.highlight
            ),
            typography = Typography(
                headingTypeface = appearance.typography.headingTypeface,
                bodyTypeface = appearance.typography.bodyTypeface
            ),
            appBar = AppBar(
                display = appearance.appBar.display,
                background = appearance.appBar.background,
                displayTitle = appearance.appBar.displayTitle
            ),
            bottomNavigation = BottomNavigation(
                display = appearance.bottomNavigation.display,
                background = appearance.bottomNavigation.background,
                label = appearance.bottomNavigation.label
            )
        ),
        navigation = Navigation(
            default = navigation.default,
            items = navigation.items.map { navigationItemDto ->
                NavigationItem(
                    id = navigationItemDto.id,
                    type = navigationItemDto.type,
                    label = navigationItemDto.label,
                    icon = navigationItemDto.icon
                )
            }
        )
    )
}
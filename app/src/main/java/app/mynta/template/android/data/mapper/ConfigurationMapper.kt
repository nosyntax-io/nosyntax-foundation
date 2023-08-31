package app.mynta.template.android.data.mapper

import app.mynta.template.android.data.source.remote.dto.ConfigurationDto
import app.mynta.template.android.domain.model.configuration.AppBar
import app.mynta.template.android.domain.model.configuration.Configuration
import app.mynta.template.android.domain.model.configuration.NavigationItem
import app.mynta.template.android.domain.model.configuration.ThemeColors

fun ConfigurationDto.toConfiguration(): Configuration {
    val configuration = app.configuration
    val navigation = configuration.navigation
    val appearance = configuration.appearance

    return Configuration(
        appId = app.appId,
        appearance = Configuration.Appearance(
            themeColors = ThemeColors(
                primary = appearance.themeColors.primary,
                secondary = appearance.themeColors.secondary,
                highlight = appearance.themeColors.highlight
            ),
            appBar = AppBar(
                display = appearance.appBar.display,
                background = appearance.appBar.background,
                displayTitle = appearance.appBar.displayTitle
            )
        ),
        navigation = Configuration.Navigation(
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
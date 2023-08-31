package app.mynta.template.android.data.mapper

import app.mynta.template.android.data.source.remote.dto.ConfigurationDto
import app.mynta.template.android.domain.model.Configuration
import app.mynta.template.android.domain.model.NavigationItem

fun ConfigurationDto.toConfiguration(): Configuration {
    val configuration = app.configuration
    val navigation = configuration.navigation
    val appearance = configuration.appearance

    return Configuration(
        appId = app.appId,
        appearance = Configuration.Appearance(
            themeColors = Configuration.Appearance.ThemeColors(
                primary = appearance.themeColors.primary,
                secondary = appearance.themeColors.secondary,
                highlight = appearance.themeColors.highlight
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
package app.mynta.template.android.data.mapper

import app.mynta.template.android.data.source.remote.dto.ConfigurationDto
import app.mynta.template.android.domain.model.Configuration
import app.mynta.template.android.domain.model.NavigationItem

fun ConfigurationDto.toConfiguration(): Configuration {
    return Configuration(
        navigationItems = navigationItems.map { navigationItem ->
            NavigationItem(
                id = navigationItem.id,
                type = navigationItem.type,
                label = navigationItem.label,
                icon = navigationItem.icon
            )
        }
    )
}
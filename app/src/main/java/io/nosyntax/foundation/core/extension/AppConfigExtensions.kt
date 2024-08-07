package io.nosyntax.foundation.core.extension

import io.nosyntax.foundation.domain.model.NavigationItem
import io.nosyntax.foundation.domain.model.app_config.AppConfig

val AppConfig.getNavigationItems: List<NavigationItem>
    get() = components.navigationBar.items + components.navigationDrawer.items
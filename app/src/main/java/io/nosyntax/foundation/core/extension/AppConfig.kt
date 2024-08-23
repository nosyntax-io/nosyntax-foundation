package io.nosyntax.foundation.core.extension

import io.nosyntax.foundation.domain.model.NavigationItem
import io.nosyntax.foundation.domain.model.app_config.AppConfig

/**
 * Retrieves a distinct list of navigation items from both the navigation bar
 * and drawer components by combining their items, filtering out any with
 * a null route, and ensuring each route appears only once.
 *
 * @return A list of distinct [NavigationItem] objects with non-null routes.
 */
val AppConfig.getDistinctNavigationItems: List<NavigationItem>
    get() = (components.navigationBar.items + components.navigationDrawer.items).filter {
        it.route != null
    }.distinctBy { it.route }

/**
 * Retrieves a list of navigation items from both the navigation bar
 * and drawer components by combining their items, filtering out items with a null route.
 *
 * @return A list of [NavigationItem] objects with non-null routes.
 */
val AppConfig.getNavigationItems: List<NavigationItem>
    get() = (components.navigationBar.items + components.navigationDrawer.items).filter {
        it.route != null
    }
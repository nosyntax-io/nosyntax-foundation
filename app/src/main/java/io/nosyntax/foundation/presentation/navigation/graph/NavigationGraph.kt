package io.nosyntax.foundation.presentation.navigation.graph

import androidx.compose.material3.DrawerState
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import io.nosyntax.foundation.domain.model.Deeplink
import io.nosyntax.foundation.domain.model.app_config.AppConfig
import io.nosyntax.foundation.domain.model.app_config.SideMenuConfig
import io.nosyntax.foundation.presentation.about.AboutScreen
import io.nosyntax.foundation.presentation.settings.SettingsScreen
import io.nosyntax.foundation.presentation.web.WebScreen

object Roles {
    const val ROLE_WEB = "webview"
    const val ROLE_SETTINGS = "settings"
    const val ROLE_ABOUT = "about"
    const val ROLE_DIVIDER = "divider"
}

@Composable
fun NavigationGraph(
    appConfig: AppConfig,
    deeplink: Deeplink,
    navController: NavHostController,
    drawerState: DrawerState
) {
    NavHost(
        navController = navController,
        startDestination = appConfig.app.components.sideMenu.items.firstOrNull()?.id ?: ""
    ) {
        appConfig.app.components.sideMenu.items.forEach { item ->
            composable(route = item.id) {
                NavigationHandler(
                    navController = navController,
                    item = item,
                    appConfig = appConfig,
                    deeplink = deeplink,
                    drawerState = drawerState
                )
            }
        }
    }
}

@Composable
fun NavigationHandler(
    navController: NavHostController,
    item: SideMenuConfig.Item,
    appConfig: AppConfig,
    deeplink: Deeplink,
    drawerState: DrawerState
) {
    when (item.type) {
        Roles.ROLE_WEB -> {
            WebScreen(
                appConfig = appConfig,
                url = deeplink.destination.ifEmpty { item.url ?: "" },
                drawerState = drawerState
            )
        }
        Roles.ROLE_SETTINGS -> {
            SettingsScreen(
                navController = navController
            )
        }

        Roles.ROLE_ABOUT -> {
            AboutScreen(
                appDescription = appConfig.app.description,
                navController = navController
            )
        }
    }
}

class NavigationActions(private val navController: NavHostController) {
    fun navigateTo(currentRoute: String, route: String) {
        if (!isUtilityScreen(route = currentRoute)) {
            clearBackStackToMainScreen()
        }
        navController.navigate(route = route)
    }

    private fun clearBackStackToMainScreen() {
        navController.popBackStack(
            destinationId = navController.graph.startDestinationId,
            inclusive = false
        )
    }
}

fun isUtilityScreen(route: String): Boolean {
    val roles = setOf(
        Roles.ROLE_SETTINGS,
        Roles.ROLE_ABOUT
    )
    return roles.any { route.startsWith(it) }
}
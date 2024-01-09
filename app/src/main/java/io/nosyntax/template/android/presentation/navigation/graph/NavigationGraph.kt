package io.nosyntax.template.android.presentation.navigation.graph

import androidx.compose.material3.DrawerState
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import io.nosyntax.template.android.domain.model.Deeplink
import io.nosyntax.template.android.domain.model.NavigationItem
import io.nosyntax.template.android.domain.model.app_config.AppConfig
import io.nosyntax.template.android.presentation.about.AboutScreen
import io.nosyntax.template.android.presentation.settings.SettingsScreen
import io.nosyntax.template.android.presentation.web.WebScreen

object Roles {
    const val ROLE_WEB = "web"
    const val ROLE_SETTINGS = "settings"
    const val ROLE_ABOUT = "about"
    const val ROLE_DIVIDER = "divider"
}

@Composable
fun NavigationGraph(
    appConfig: AppConfig,
    deeplink: Deeplink,
    navController: NavHostController,
    navigationItems: List<NavigationItem>,
    drawerState: DrawerState
) {
    NavHost(
        navController = navController,
        startDestination = navigationItems.firstOrNull()?.route ?: ""
    ) {
        navigationItems.forEach { item ->
            composable(route = item.route) {
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
    item: NavigationItem,
    appConfig: AppConfig,
    deeplink: Deeplink,
    drawerState: DrawerState
) {
    when {
        item.route.startsWith(Roles.ROLE_WEB) -> {
            WebScreen(
                appConfig = appConfig,
                url = deeplink.destination.ifEmpty { item.deeplink },
                drawerState = drawerState
            )
        }
        item.route.startsWith(Roles.ROLE_SETTINGS) -> {
            SettingsScreen(
                navController = navController
            )
        }
        item.route.startsWith(Roles.ROLE_ABOUT) -> {
            AboutScreen(
                appConfig = appConfig,
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
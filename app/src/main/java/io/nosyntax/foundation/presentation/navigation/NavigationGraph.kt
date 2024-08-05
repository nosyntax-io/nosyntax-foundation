package io.nosyntax.foundation.presentation.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.material3.DrawerState
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import io.nosyntax.foundation.domain.model.Deeplink
import io.nosyntax.foundation.domain.model.app_config.AppConfig
import io.nosyntax.foundation.presentation.about.AboutScreen
import io.nosyntax.foundation.presentation.settings.SettingsScreen
import io.nosyntax.foundation.presentation.web.WebScreen

@Composable
fun NavigationGraph(
    appConfig: AppConfig,
    navController: NavHostController,
    drawerState: DrawerState,
    deeplink: Deeplink?
) {
    val startDestination = deeplink?.let { "deeplink" } ?: appConfig.settings.entryPage

    NavHost(
        navController = navController,
        startDestination = startDestination,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None }
    ) {
        appConfig.components.sideMenu.items.filter { it.route != null }.forEach { item ->
            composable(route = item.route!!) {
                when (item.type) {
                    "webview" -> WebScreen(
                        appConfig = appConfig,
                        drawerState = drawerState,
                        url = item.action.orEmpty()
                    )
                    "settings" -> SettingsScreen(
                        appConfig = appConfig,
                        navController = navController
                    )
                    "about" -> AboutScreen(
                        appConfig = appConfig,
                        navController = navController
                    )
                }
            }
        }
        composable(route = "deeplink") {
            WebScreen(
                appConfig = appConfig,
                drawerState = drawerState,
                url = deeplink?.destination.orEmpty()
            )
        }
        composable(route = "about") {
            AboutScreen(
                appConfig = appConfig,
                navController = navController
            )
        }
    }
}
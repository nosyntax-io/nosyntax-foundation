package io.nosyntax.foundation.presentation.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.material3.DrawerState
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import io.nosyntax.foundation.core.extension.getNavigationItems
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
        appConfig.getNavigationItems.filter { it.route != null }.forEach { item ->
            composable(route = item.route!!) {
                when (item.type) {
                    "webview" -> WebScreen(
                        appConfig = appConfig,
                        url = item.action.orEmpty(),
                        captureBackPresses = !drawerState.isOpen
                    )
                    "settings" -> SettingsScreen(
                        appConfig = appConfig,
                        navigateToAbout = {
                            navController.navigate("about")
                        }
                    )
                    "about" -> AboutScreen(
                        appConfig = appConfig
                    )
                }
            }
        }
        composable(route = "deeplink") {
            WebScreen(
                appConfig = appConfig,
                url = deeplink?.destination.orEmpty(),
                captureBackPresses = !drawerState.isOpen
            )
        }
        composable(route = "about") {
            AboutScreen(
                appConfig = appConfig
            )
        }
    }
}
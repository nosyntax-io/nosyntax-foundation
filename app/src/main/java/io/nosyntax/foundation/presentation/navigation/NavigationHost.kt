package io.nosyntax.foundation.presentation.navigation

import androidx.compose.material3.DrawerState
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import io.nosyntax.foundation.core.constant.Constants
import io.nosyntax.foundation.core.extension.getDistinctNavigationItems
import io.nosyntax.foundation.domain.model.Deeplink
import io.nosyntax.foundation.domain.model.app_config.AppConfig
import io.nosyntax.foundation.presentation.screen.about.AboutScreen
import io.nosyntax.foundation.presentation.screen.settings.SettingsScreen
import io.nosyntax.foundation.presentation.screen.web.WebScreen

@Composable
fun NavigationHost(
    appConfig: AppConfig,
    navController: NavHostController,
    drawerState: DrawerState,
    deeplink: Deeplink?
) {
    val startDestination = deeplink?.let {
        Constants.Routes.DEEPLINK
    } ?: appConfig.settings.entryPage

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        appConfig.getDistinctNavigationItems.forEach { item ->
            composable(route = item.route!!) {
                when (item.type) {
                    Constants.Navigation.WEBVIEW -> WebScreen(
                        appConfig = appConfig,
                        url = item.action.orEmpty(),
                        captureBackPresses = !drawerState.isOpen
                    )
                    Constants.Navigation.SETTINGS -> SettingsScreen(
                        appConfig = appConfig,
                        navigateToAbout = {
                            navController.navigate(
                                route = Constants.Routes.ABOUT
                            )
                        }
                    )
                    Constants.Navigation.ABOUT -> AboutScreen(
                        appConfig = appConfig
                    )
                }
            }
        }
        composable(route = Constants.Routes.DEEPLINK) {
            WebScreen(
                appConfig = appConfig,
                url = deeplink?.destination.orEmpty(),
                captureBackPresses = !drawerState.isOpen
            )
        }
        composable(route = Constants.Routes.ABOUT) {
            AboutScreen(
                appConfig = appConfig
            )
        }
    }
}
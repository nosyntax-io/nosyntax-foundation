package io.nosyntax.foundation.presentation.navigation.graph

import android.content.Context
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.material3.DrawerState
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import io.nosyntax.foundation.core.utility.Intents.openDial
import io.nosyntax.foundation.core.utility.Intents.openEmail
import io.nosyntax.foundation.core.utility.Intents.openSMS
import io.nosyntax.foundation.core.utility.Intents.openUrl
import io.nosyntax.foundation.domain.model.Deeplink
import io.nosyntax.foundation.domain.model.app_config.AppConfig
import io.nosyntax.foundation.presentation.about.AboutScreen
import io.nosyntax.foundation.presentation.settings.SettingsScreen
import io.nosyntax.foundation.presentation.web.WebScreen

@Composable
fun NavigationGraph(
    appConfig: AppConfig,
    navController: NavHostController,
    deeplink: Deeplink?,
    drawerState: DrawerState
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
                        url = item.action.orEmpty(),
                        drawerState = drawerState
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
                url = deeplink?.destination.orEmpty(),
                drawerState = drawerState
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

// TODO: Find a cleaner approach for the navigator class 
class Navigator(private val context: Context, private val navController: NavHostController) {
    fun navigate(currentRoute: String, route: String) {
        if (!isUtilityScreen(currentRoute)) {
            clearBackStackToMainScreen()
        }
        navController.navigate(route)
    }

    fun open(type: String, action: String) {
        when (type) {
            "browser" -> context.openUrl(url = action)
            "mail" -> context.openEmail(url = action)
            "dial" -> context.openDial(url = action)
            "sms" -> context.openSMS(url = action)
        }
    }

    private fun clearBackStackToMainScreen() {
        navController.popBackStack(
            destinationId = navController.graph.startDestinationId,
            inclusive = false
        )
    }
}

// TODO: Handle utility screens in a cleaner way
fun isUtilityScreen(route: String): Boolean {
    return setOf("settings", "about").any { route.startsWith(it) }
}
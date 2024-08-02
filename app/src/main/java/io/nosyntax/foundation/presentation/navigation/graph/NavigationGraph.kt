package io.nosyntax.foundation.presentation.navigation.graph

import android.content.Context
import androidx.compose.material3.DrawerState
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import io.nosyntax.foundation.core.utility.Intents.openDial
import io.nosyntax.foundation.core.utility.Intents.openEmail
import io.nosyntax.foundation.core.utility.Intents.openSMS
import io.nosyntax.foundation.domain.model.Deeplink
import io.nosyntax.foundation.domain.model.app_config.AppConfig
import io.nosyntax.foundation.domain.model.app_config.Components
import io.nosyntax.foundation.presentation.about.AboutScreen
import io.nosyntax.foundation.presentation.settings.SettingsScreen
import io.nosyntax.foundation.presentation.web.WebScreen

@Composable
fun NavigationGraph(
    appConfig: AppConfig,
    deeplink: Deeplink,
    navController: NavHostController,
    drawerState: DrawerState
) {
    NavHost(
        navController = navController,
        startDestination = appConfig.settings.entryPage
    ) {
        appConfig.components.sideMenu.items.forEach { item ->
            composable(route = item.route) {
                ComposableContent(
                    appConfig = appConfig,
                    deeplink = deeplink,
                    item = item,
                    navController = navController,
                    drawerState = drawerState
                )
            }
        }
    }
}

@Composable
fun ComposableContent(
    appConfig: AppConfig,
    deeplink: Deeplink,
    item: Components.SideMenu.Item,
    navController: NavHostController,
    drawerState: DrawerState
) {
    when {
        item.route.startsWith("web") -> {
            WebScreen(
                appConfig = appConfig,
                url = deeplink.destination.ifEmpty { item.action ?: "" },
                drawerState = drawerState
            )
        }
        item.route.startsWith("settings") -> {
            SettingsScreen(
                appConfig = appConfig,
                navController = navController
            )
        }
        item.route.startsWith("about") -> {
            AboutScreen(
                appConfig = appConfig,
                navController = navController
            )
        }
    }
}

class Navigator(private val context: Context, private val navController: NavHostController) {
    fun navigate(currentRoute: String, route: String) {
        if (!isUtilityScreen(currentRoute)) {
            clearBackStackToMainScreen()
        }
        navController.navigate(route)
    }

    fun open(route: String, action: String) {
        when {
            route.startsWith("mail") -> context.openEmail(url = action)
            route.startsWith("dial") -> context.openDial(url = action)
            route.startsWith("sms") -> context.openSMS(url = action)
        }
    }

    private fun clearBackStackToMainScreen() {
        navController.popBackStack(
            destinationId = navController.graph.startDestinationId,
            inclusive = false
        )
    }
}

fun isUtilityScreen(route: String): Boolean {
    return setOf("settings", "about").any { route.startsWith(it) }
}
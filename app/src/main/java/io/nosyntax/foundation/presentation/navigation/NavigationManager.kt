package io.nosyntax.foundation.presentation.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import io.nosyntax.foundation.core.extension.openContent
import io.nosyntax.foundation.core.extension.openDialer
import io.nosyntax.foundation.core.extension.openMailer
import io.nosyntax.foundation.core.extension.openSMS
import io.nosyntax.foundation.domain.model.NavigationItem

@Composable
fun rememberNavManager(
    navController: NavHostController = rememberNavController()
): NavigationManager {
    val context = LocalContext.current

    return remember(navController) {
        NavigationManager(
            context = context,
            navController = navController
        )
    }
}

@Stable
class NavigationManager(
    val context: Context,
    val navController: NavHostController
) {
    val currentRoute: String
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination?.route.orEmpty()

    fun handleNavItemClick(item: NavigationItem) {
        if (item.type in setOf("browser", "mail", "dial", "sms")) {
            item.action?.let { action -> performAction(item.type, action) }
        } else {
            item.route?.let { route -> navigate(route, item.type) }
        }
    }

    fun performAction(type: String, action: String) {
        when (type) {
            "browser" -> context.openContent(action)
            "mail" -> context.openMailer(action)
            "dial" -> context.openDialer(action)
            "sms" -> context.openSMS(action)
        }
    }

    fun navigate(route: String, type: String) {
        navController.navigate(route = route) {
            if (type in setOf("settings", "about")) {
                popUpTo(navController.currentBackStackEntry?.destination?.route ?: route) {
                    inclusive = false
                }
            } else {
                navController.graph.startDestinationRoute?.let { route ->
                    popUpTo(route) {
                        saveState = true
                    }
                }
            }
            launchSingleTop = true
            restoreState = true
        }
    }
}
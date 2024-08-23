package io.nosyntax.foundation.presentation.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import io.nosyntax.foundation.core.constant.Constants
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
        when (item.type) {
            in setOf(
                Constants.Navigation.BROWSER,
                Constants.Navigation.MAIL,
                Constants.Navigation.DIAL,
                Constants.Navigation.SMS
            ) -> item.action?.let { action -> performAction(item.type, action) }

            else -> item.route?.let { route -> navigate(route, item.type) }
        }
    }

    fun performAction(type: String, action: String) {
        when (type) {
            Constants.Navigation.BROWSER -> context.openContent(action)
            Constants.Navigation.MAIL -> context.openMailer(action)
            Constants.Navigation.DIAL -> context.openDialer(action)
            Constants.Navigation.SMS -> context.openSMS(action)
        }
    }

    fun navigate(route: String, type: String) {
        navController.navigate(route = route) {
            if (type in setOf(Constants.Navigation.SETTINGS, Constants.Navigation.ABOUT)) {
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
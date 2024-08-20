package io.nosyntax.foundation.presentation.navigation

import android.content.Context
import androidx.navigation.NavHostController
import io.nosyntax.foundation.core.util.openContent
import io.nosyntax.foundation.core.util.openDialer
import io.nosyntax.foundation.core.util.openMailer
import io.nosyntax.foundation.core.util.openSMS
import io.nosyntax.foundation.domain.model.NavigationItem

class NavigationHandler(
    private val context: Context,
    private val navController: NavHostController
) {
    fun onItemClick(item: NavigationItem) {
        if (item.type in setOf("browser", "mail", "dial", "sms")) {
            item.action?.let { performAction(item.type, it) }
        } else {
            item.route?.let { navigate(it, item.type) }
        }
    }

    private fun performAction(type: String, action: String) {
        when (type) {
            "browser" -> context.openContent(action)
            "mail" -> context.openMailer(action)
            "dial" -> context.openDialer(action)
            "sms" -> context.openSMS(action)
        }
    }

    private fun navigate(route: String, type: String) {
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
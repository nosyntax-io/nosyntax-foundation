package io.nosyntax.foundation.presentation.navigation

import android.content.Context
import androidx.navigation.NavHostController
import io.nosyntax.foundation.core.utility.Intents.openDial
import io.nosyntax.foundation.core.utility.Intents.openEmail
import io.nosyntax.foundation.core.utility.Intents.openSMS
import io.nosyntax.foundation.core.utility.Intents.openUrl
import io.nosyntax.foundation.domain.model.app_config.Components

class SideMenuNavigator(private val context: Context, private val navController: NavHostController) {
    fun handleItemClick(item: Components.SideMenu.Item) {
        if (item.type in setOf("browser", "mail", "dial", "sms")) {
            performAction(item.type, item.action)
        } else {
            navigate(item.route!!, item.type)
        }
    }

    private fun performAction(type: String, action: String?) {
        when (type) {
            "browser" -> context.openUrl(action.orEmpty())
            "mail" -> context.openEmail(action.orEmpty())
            "dial" -> context.openDial(action.orEmpty())
            "sms" -> context.openSMS(action.orEmpty())
        }
    }

    private fun navigate(route: String?, type: String) {
        requireNotNull(route) { "Route must not be null" }

        if (type !in setOf("settings", "about")) {
            navController.popBackStack(
                destinationId = navController.graph.startDestinationId,
                inclusive = false
            )
        }
        navController.navigate(route)
    }
}
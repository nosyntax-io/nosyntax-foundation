package app.mynta.template.android.presentation.navigation.graph

import androidx.compose.material3.DrawerState
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import app.mynta.template.android.domain.model.NavigationItem
import app.mynta.template.android.domain.model.app_config.AppConfig
import app.mynta.template.android.presentation.about.AboutScreen
import app.mynta.template.android.presentation.policies.PoliciesScreen
import app.mynta.template.android.presentation.web.WebScreen

object Routes {
    const val ROUTE_HOME = "home"
    const val ROUTE_PRIVACY_POLICY = "privacy_policy"
    const val ROUTE_TERMS_OF_USE = "terms_of_use"
    const val ROUTE_ABOUT = "about"
}

object Roles {
    const val ROLE_WEB = "web"
    const val ROLE_POLICIES = "policies"
    const val ROLE_ABOUT = "about"
    const val ROLE_DIVIDER = "divider"
    const val ROLE_MORE = "more"
}

object Types {
    const val TYPE_REGULAR = "regular"
    const val TYPE_CORE = "core"
    const val TYPE_OTHER = "other"
}

@Composable
fun NavigationGraph(
    appConfig: AppConfig,
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
                when (item.role) {
                    Roles.ROLE_WEB -> {
                        WebScreen(
                            appConfig = appConfig,
                            url = item.module?.deeplink ?: "https://example.com",
                            drawerState = drawerState
                        )
                    }
                    Roles.ROLE_POLICIES -> {
                        PoliciesScreen(request = item.route)
                    }
                    Roles.ROLE_ABOUT -> {
                        AboutScreen(aboutPageConfig = appConfig.aboutPage)
                    }
                }
            }
        }
    }
}

class NavigationActions(private val navController: NavHostController) {
    fun navigateTo(currentRoute: String, route: String) {
        if (!isUtilityScreen(currentRoute = currentRoute)) {
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

fun isUtilityScreen(currentRoute: String): Boolean {
    return currentRoute in setOf(
        Routes.ROUTE_ABOUT,
        Routes.ROUTE_PRIVACY_POLICY,
        Routes.ROUTE_TERMS_OF_USE
    )
}
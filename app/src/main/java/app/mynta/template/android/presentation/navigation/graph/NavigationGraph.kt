package app.mynta.template.android.presentation.navigation.graph

import androidx.compose.material3.DrawerState
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import app.mynta.template.android.domain.model.Deeplink
import app.mynta.template.android.domain.model.app_config.AppConfig
import app.mynta.template.android.domain.model.app_config.SideMenuConfig
import app.mynta.template.android.presentation.about.AboutScreen
import app.mynta.template.android.presentation.policies.PoliciesScreen
import app.mynta.template.android.presentation.web.WebScreen

object Routes {
    const val ROUTE_HOME = "home"
}

object Roles {
    const val ROLE_WEB = "web"
    const val ROLE_PRIVACY_POLICY = "privacy"
    const val ROLE_TERMS_OF_USE = "terms"
    const val ROLE_ABOUT = "about"
    const val ROLE_DIVIDER = "divider"
    const val ROLE_MORE = "more"
}

@Composable
fun NavigationGraph(
    appConfig: AppConfig,
    deeplink: Deeplink,
    navController: NavHostController,
    navigationItems: List<SideMenuConfig.Item>,
    drawerState: DrawerState
) {
    NavHost(
        navController = navController,
        startDestination = navigationItems.firstOrNull()?.route.toString()
    ) {
        navigationItems.forEach { item ->
            composable(route = item.route) {
                NavigationHandler(
                    navController = navController,
                    item = item,
                    appConfig = appConfig,
                    deeplink = deeplink,
                    drawerState = drawerState
                )
            }
        }
    }
}

@Composable
fun NavigationHandler(
    navController: NavHostController,
    item: SideMenuConfig.Item,
    appConfig: AppConfig,
    deeplink: Deeplink,
    drawerState: DrawerState
) {
    when {
        item.route.startsWith(Roles.ROLE_WEB) -> {
            WebScreen(
                appConfig = appConfig,
                url = deeplink.destination.ifEmpty { item.deeplink },
                drawerState = drawerState
            )
        }
        item.route.startsWith(Roles.ROLE_PRIVACY_POLICY) -> {
            PoliciesScreen(
                navController = navController,
                request = "privacy_policy"
            )
        }
        item.route.startsWith(Roles.ROLE_TERMS_OF_USE) -> {
            PoliciesScreen(
                navController = navController,
                request = "terms_of_use"
            )
        }
        item.route.startsWith(Roles.ROLE_ABOUT) -> {
            AboutScreen(
                aboutPageConfig = appConfig.aboutPage,
                navController = navController
            )
        }
    }
}

class NavigationActions(private val navController: NavHostController) {
    fun navigateTo(currentRoute: String, route: String) {
        if (!isUtilityScreen(route = currentRoute)) {
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

fun isUtilityScreen(route: String): Boolean {
    val roles = setOf(
        Roles.ROLE_PRIVACY_POLICY,
        Roles.ROLE_TERMS_OF_USE,
        Roles.ROLE_ABOUT
    )
    return roles.any { route.startsWith(it) }
}
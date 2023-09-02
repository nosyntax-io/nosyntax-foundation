package app.mynta.template.android.presentation.navigation.graph

import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import app.mynta.template.android.domain.model.NavigationItem
import app.mynta.template.android.domain.model.app_config.AppConfig
import app.mynta.template.android.presentation.about.AboutScreen
import app.mynta.template.android.presentation.policies.PoliciesScreen
import app.mynta.template.android.presentation.web.WebScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeNavigationGraph(
    appConfig: AppConfig,
    navController: NavHostController,
    navigationItems: List<NavigationItem>,
    drawerState: DrawerState
) {
    NavHost(
        navController = navController,
        route = Routes.ROUTE_HOME,
        startDestination = navigationItems.firstOrNull()?.id ?: ""
    ) {
        navigationItems.forEach { item ->
            composable(route = item.id) {
                when (item.role) {
                    "web" -> {
                        WebScreen(
                            url = "https://google.com",
                            isDrawerOpen = drawerState.isOpen
                        )
                    }
                    "policies" -> {
                        PoliciesScreen(
                            request = item.id
                        )
                    }
                    "about" -> {
                        AboutScreen(
                            aboutPageConfig = appConfig.aboutPage
                        )
                    }
                }
            }
        }
    }
}

object Routes {
    const val ROUTE_HOME = "home"
    const val ROUTE_PRIVACY_POLICY = "privacy_policy"
    const val ROUTE_TERMS_OF_USE = "terms_of_use"
    const val ROUTE_ABOUT = "about"
}
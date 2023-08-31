package app.mynta.template.android.presentation.navigation.graph

import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import app.mynta.template.android.domain.model.configuration.NavigationItem
import app.mynta.template.android.presentation.home.HomeScreen
import app.mynta.template.android.presentation.web.WebScreen

@Composable
fun MainNavigationGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Routes.ROUTE_HOME
    ) {
        composable(route = Routes.ROUTE_HOME) {
            HomeScreen()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeNavigationGraph(
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
                when (item.type) {
                    "web" -> {
                        WebScreen(
                            url = "https://google.com",
                            isDrawerOpen = drawerState.isOpen
                        )
                    }
                }
            }
        }
    }
}

object Routes {
    const val ROUTE_HOME = "home"
}
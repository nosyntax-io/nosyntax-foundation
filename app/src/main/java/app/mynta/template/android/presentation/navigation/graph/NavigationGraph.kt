package app.mynta.template.android.presentation.navigation.graph

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import app.mynta.template.android.domain.model.NavigationItem
import app.mynta.template.android.presentation.home.HomeScreen
import app.mynta.template.android.presentation.web.components.WebViewComponent

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

@Composable
fun HomeNavigationGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    navigationItems: List<NavigationItem>
) {
    NavHost(
        navController = navController,
        route = Routes.ROUTE_HOME,
        startDestination = navigationItems.firstOrNull()?.id ?: ""
    ) {
        navigationItems.forEach { item ->
            composable(route = item.id) {
                WebViewComponent(modifier = modifier, url = "https://facebook.com")
            }
        }
    }
}

object Routes {
    const val ROUTE_HOME = "home"
}
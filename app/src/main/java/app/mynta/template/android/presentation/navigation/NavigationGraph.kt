package app.mynta.template.android.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import app.mynta.template.android.domain.model.MenuItem
import app.mynta.template.android.presentation.web.components.WebViewComponent

@Composable
fun MainNavigationGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "home") {

    }
}

@Composable
fun HomeNavigationGraph(modifier: Modifier = Modifier, navController: NavHostController, navDrawerItem: List<MenuItem>) {
    NavHost(navController = navController, route = Routes.ROUTE_HOME, startDestination = navDrawerItem.firstOrNull()?.route ?: "") {
        navDrawerItem.forEach { item ->
            composable(route = item.route) {
                WebViewComponent(modifier = modifier, url = "https://facebook.com")
            }
        }
    }
}

object Routes {
    const val ROUTE_HOME = "home"
}
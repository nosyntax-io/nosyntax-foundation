package app.mynta.template.android.presentation.home

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import app.mynta.template.android.core.components.AppBar
import app.mynta.template.android.core.components.NavigationItem
import app.mynta.template.android.domain.model.MenuItem
import app.mynta.template.android.presentation.navigation.HomeNavigationGraph
import app.mynta.template.android.presentation.navigation.Routes
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navigationItems: List<MenuItem>) {
    val navController = rememberNavController()
    val coroutineScope = rememberCoroutineScope()

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val currentNavBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentNavBackStackEntry?.destination?.route ?: Routes.ROUTE_HOME

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = drawerState.isOpen,
        drawerContent = {
            ModalDrawerSheet {
                navigationItems.forEach { item ->
                    NavigationItem(currentRoute = currentRoute, item = item, onClick = {
                        navController.navigate(route = item.route)
                        coroutineScope.launch { drawerState.close() }
                    })
                }
            }
        },
        content = {
            Scaffold(
                topBar = {
                    AppBar(title = "Good to Know") {
                        coroutineScope.launch { drawerState.open() }
                    }
                },
                content = { inlinePadding ->
                    HomeNavigationGraph(
                        modifier = Modifier
                            .padding(inlinePadding)
                            .verticalScroll(rememberScrollState()),
                        navController = navController,
                        navDrawerItem = navigationItems)
                }
            )
        }
    )
}
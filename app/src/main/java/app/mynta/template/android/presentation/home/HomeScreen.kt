package app.mynta.template.android.presentation.home

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import app.mynta.template.android.core.components.AppBar
import app.mynta.template.android.domain.model.app_config.AppConfig
import app.mynta.template.android.presentation.navigation.component.SideMenu
import app.mynta.template.android.domain.model.NavigationItem
import app.mynta.template.android.presentation.main.MainViewModel
import app.mynta.template.android.presentation.navigation.component.BottomBar
import app.mynta.template.android.presentation.navigation.graph.HomeNavigationGraph
import app.mynta.template.android.presentation.navigation.graph.Routes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: MainViewModel = viewModel(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    navController: NavHostController = rememberNavController()
) {
    val appConfig by viewModel.appConfigUI.collectAsState()
    val currentNavBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentNavBackStackEntry?.destination?.route ?: Routes.ROUTE_HOME
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    appConfig?.let { config ->
        val components = config.appearance.components
        val navigationItems = config.navigation.items

        val sideMenuConfig = components.sideMenu
        if (sideMenuConfig.display) {
            SideMenu(
                sideMenuConfig = sideMenuConfig,
                coroutineScope = coroutineScope,
                navController = navController,
                currentRoute = currentRoute,
                navigationItems = navigationItems,
                drawerState = drawerState,
                content = {
                    HomeContent(
                        appConfig = config,
                        coroutineScope = coroutineScope,
                        navController = navController,
                        currentRoute = currentRoute,
                        navigationItems = navigationItems,
                        drawerState = drawerState,
                    )
                }
            )
        } else {
            HomeContent(
                appConfig = config,
                coroutineScope = coroutineScope,
                navController = navController,
                currentRoute = currentRoute,
                navigationItems = navigationItems,
                drawerState = drawerState,
            )
        }
    }

    BackHandler(enabled = drawerState.isOpen) {
        coroutineScope.launch {
            drawerState.close()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeContent(
    appConfig: AppConfig,
    coroutineScope: CoroutineScope,
    navController: NavHostController,
    currentRoute: String,
    navigationItems: List<NavigationItem>,
    drawerState: DrawerState
) {
    val selectedItem = navigationItems.find { it.id == currentRoute }
    val components = appConfig.appearance.components

    Scaffold(
        topBar = {
            val appBarConfig = components.appBar
            if (appBarConfig.display) {
                val showBackIcon = when (currentRoute) {
                    Routes.ROUTE_ABOUT -> true
                    else -> false
                }
                AppBar(
                    appBarConfig = appBarConfig,
                    title = selectedItem?.label ?: "",
                    showBackButton = showBackIcon,
                    onNavigationActionClick = {
                        if (!showBackIcon) {
                            coroutineScope.launch {
                                drawerState.open()
                            }
                        } else {
                            navController.popBackStack()
                        }
                    }
                )
            }
        },
        content = { inlinePadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(inlinePadding)
            ) {
                HomeNavigationGraph(
                    navController = navController,
                    navigationItems = navigationItems,
                    drawerState = drawerState
                )
            }
        },
        bottomBar = {
            val bottomBarConfig = components.bottomBar
            if (bottomBarConfig.display) {
                BottomBar(
                    bottomBarConfig = bottomBarConfig,
                    navController = navController,
                    currentRoute = currentRoute,
                    navigationItems = navigationItems
                )
            }
        }
    )
}
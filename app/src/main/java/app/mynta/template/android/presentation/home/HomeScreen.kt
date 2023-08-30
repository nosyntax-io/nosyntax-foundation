package app.mynta.template.android.presentation.home

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import app.mynta.template.android.R
import app.mynta.template.android.core.components.AppBar
import app.mynta.template.android.presentation.navigation.component.NavigationDrawer
import app.mynta.template.android.domain.model.NavigationItem
import app.mynta.template.android.presentation.main.MainViewModel
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
    val configuration by viewModel.configurationUI.collectAsState()
    val currentNavBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentNavBackStackEntry?.destination?.route ?: Routes.ROUTE_HOME
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    configuration?.let { data ->
        NavigationDrawer(
            coroutineScope = coroutineScope,
            navController = navController,
            currentRoute = currentRoute,
            navigationItems = data.navigationItems,
            drawerState = drawerState,
            content = {
                HomeContent(
                    coroutineScope = coroutineScope,
                    drawerState = drawerState,
                    navController = navController,
                    navigationItems = data.navigationItems
                )
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeContent(
    coroutineScope: CoroutineScope,
    navController: NavHostController,
    navigationItems: List<NavigationItem>,
    drawerState: DrawerState
) {
    Scaffold(
        topBar = {
            AppBar(title = stringResource(id = R.string.app_name)) {
                coroutineScope.launch { drawerState.open() }
            }
        },
        content = { inlinePadding ->
            HomeNavigationGraph(
                modifier = Modifier
                    .padding(inlinePadding)
                    .verticalScroll(rememberScrollState()),
                navController = navController,
                navigationItems = navigationItems)
        }
    )
}
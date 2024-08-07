package io.nosyntax.foundation.presentation.main

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import io.nosyntax.foundation.R
import io.nosyntax.foundation.core.component.AppBar
import io.nosyntax.foundation.core.component.NavigationBar
import io.nosyntax.foundation.core.component.NavigationAction
import io.nosyntax.foundation.core.component.SnackbarComponent
import io.nosyntax.foundation.core.utility.Utilities.findActivity
import io.nosyntax.foundation.domain.model.Deeplink
import io.nosyntax.foundation.core.component.NavigationDrawer
import io.nosyntax.foundation.domain.model.app_config.AppConfig
import io.nosyntax.foundation.domain.model.app_config.Components
import io.nosyntax.foundation.presentation.navigation.NavigationGraph
import io.nosyntax.foundation.presentation.navigation.NavigationHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun MainScreen(
    viewModel: MainViewModel = viewModel(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    navController: NavHostController = rememberNavController(),
    deeplink: Deeplink? = null
) {
    val context = LocalContext.current
    val appConfig by viewModel.appConfig.collectAsState()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route.orEmpty()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val navHandler = remember { NavigationHandler(context, navController) }

    appConfig.response?.let { config ->
        val content: @Composable () -> Unit = {
            MainContent(
                config = config,
                coroutineScope = coroutineScope,
                navController = navController,
                navHandler = navHandler,
                currentRoute = currentRoute,
                drawerState = drawerState,
                deeplink = deeplink
            )
        }

        config.components.navigationDrawer.takeIf { it.visible }?.let {
            NavigationDrawer(
                config = it,
                currentRoute = currentRoute,
                drawerState = drawerState,
                content = content,
                onItemClick = { item ->
                    navHandler.onItemClick(item)
                    coroutineScope.launch { drawerState.close() }
                }
            )
        } ?: content()
    }

    BackHandler(enabled = drawerState.isOpen, onBack = {
        coroutineScope.launch { drawerState.close() }
    })
}

@Composable
private fun MainContent(
    config: AppConfig,
    coroutineScope: CoroutineScope,
    navController: NavHostController,
    navHandler: NavigationHandler,
    currentRoute: String,
    drawerState: DrawerState,
    deeplink: Deeplink?
) {
    Scaffold(
        snackbarHost = {
            MainSnackbarHost()
        },
        topBar = {
            MainAppBar(
                config = config.components,
                coroutineScope = coroutineScope,
                navController = navController,
                currentRoute = currentRoute,
                drawerState = drawerState
            )
        },
        content = { padding ->
            Box(modifier = Modifier.padding(padding)) {
                NavigationGraph(
                    appConfig = config,
                    navController = navController,
                    drawerState = drawerState,
                    deeplink = deeplink
                )
            }
        },
        bottomBar = {
            MainNavigationBar(
                config = config.components.navigationBar,
                navHandler = navHandler,
                currentRoute = currentRoute
            )
        }
    )
}

@Composable
private fun MainSnackbarHost() {
    SnackbarHost(
        hostState = remember { SnackbarHostState() },
        snackbar = { SnackbarComponent(it.visuals.message) }
    )
}

@Composable
private fun MainAppBar(
    config: Components,
    coroutineScope: CoroutineScope,
    navController: NavHostController,
    currentRoute: String,
    drawerState: DrawerState
) {
    val context = LocalContext.current

    config.appBar.takeIf { it.visible }?.let {
        val title = when (currentRoute) {
            "about" -> stringResource(id = R.string.about_us)
            else -> (config.navigationDrawer.items + config.navigationBar.items).find { item ->
                item.route == currentRoute
            }?.label.orEmpty()
        }

        val action = if (listOf("settings", "about").any { currentRoute.startsWith(it) }) {
            NavigationAction.Back {
                (context.findActivity() as MainActivity).showInterstitial {
                    navController.popBackStack()
                }
            }
        } else {
            NavigationAction.Menu(enabled = true) {
                coroutineScope.launch { drawerState.open() }
            }
        }

        AppBar(
            config = it,
            title = title,
            navigationAction = action
        )
    }
}

@Composable
private fun MainNavigationBar(
    config: Components.NavigationBar,
    navHandler: NavigationHandler,
    currentRoute: String
) {
    if (config.visible && listOf("settings", "about").none { currentRoute.startsWith(it) }) {
        NavigationBar(
            config = config,
            currentRoute = currentRoute,
            onItemClick = { item ->
                navHandler.onItemClick(item)
            }
        )
    }
}
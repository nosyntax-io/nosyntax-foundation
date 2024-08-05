package io.nosyntax.foundation.presentation.main

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import io.nosyntax.foundation.core.component.BottomBar
import io.nosyntax.foundation.core.component.NavigationAction
import io.nosyntax.foundation.core.component.SnackbarComponent
import io.nosyntax.foundation.core.utility.Utilities.findActivity
import io.nosyntax.foundation.domain.model.Deeplink
import io.nosyntax.foundation.domain.model.app_config.AppConfig
import io.nosyntax.foundation.core.component.SideMenu
import io.nosyntax.foundation.presentation.navigation.NavigationGraph
import io.nosyntax.foundation.presentation.navigation.SideMenuNavigator
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
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val navigator = remember { SideMenuNavigator(context, navController) }

    appConfig.response?.let { config ->
        val content: @Composable () -> Unit = {
            MainContent(
                appConfig = config,
                coroutineScope = coroutineScope,
                navController = navController,
                currentRoute = currentRoute,
                deeplink = deeplink,
                drawerState = drawerState
            )
        }

        config.components.sideMenu.takeIf { it.visible }?.let {
            SideMenu(
                config = config.components.sideMenu,
                currentRoute = currentRoute,
                drawerState = drawerState,
                content = content,
                onItemClick = { item ->
                    navigator.handleItemClick(item)
                    coroutineScope.launch { drawerState.close() }
                },
            )
        } ?: content()
    }

    BackHandler(enabled = drawerState.isOpen, onBack = {
        coroutineScope.launch { drawerState.close() }
    })
}

@Composable
private fun MainContent(
    appConfig: AppConfig,
    coroutineScope: CoroutineScope,
    navController: NavHostController,
    currentRoute: String,
    deeplink: Deeplink?,
    drawerState: DrawerState
) {
    val context = LocalContext.current
    val components = appConfig.components

    Scaffold(
        snackbarHost = {
            SnackbarHost(
                hostState = remember { SnackbarHostState() },
                snackbar = { SnackbarComponent(it.visuals.message) }
            )
        },
        topBar = {
            if (components.appBar.visible) {
                val title = when (currentRoute) {
                    "about" -> stringResource(id = R.string.about_us)
                    else -> components.sideMenu.items.find {
                        it.route == currentRoute
                    }?.label.orEmpty()
                }

                val action = if (currentRoute.startsWith("settings") || currentRoute.startsWith("about")) {
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
                    config = components.appBar,
                    title = title,
                    navigationAction = action
                )
            }
        },
        content = { inline ->
            Column(modifier = Modifier
                .fillMaxSize()
                .padding(inline)) {
                NavigationGraph(
                    appConfig = appConfig,
                    navController = navController,
                    deeplink = deeplink,
                    drawerState = drawerState
                )
            }
        },
        bottomBar = {
            if (components.bottomMenu.visible) {
                BottomBar(
                    config = components.bottomMenu,
                    currentRoute = currentRoute,
                    onItemClick = {

                    }
                )
            }
        }
    )
}
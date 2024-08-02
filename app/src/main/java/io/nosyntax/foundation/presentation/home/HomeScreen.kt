package io.nosyntax.foundation.presentation.home

import android.content.Context
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import io.nosyntax.foundation.core.component.AppBar
import io.nosyntax.foundation.core.component.NavigationAction
import io.nosyntax.foundation.core.component.SnackbarComponent
import io.nosyntax.foundation.core.utility.Utilities.findActivity
import io.nosyntax.foundation.domain.model.Deeplink
import io.nosyntax.foundation.domain.model.app_config.AppConfig
import io.nosyntax.foundation.presentation.main.MainActivity
import io.nosyntax.foundation.presentation.navigation.component.SideMenu
import io.nosyntax.foundation.presentation.main.MainViewModel
import io.nosyntax.foundation.presentation.navigation.graph.NavigationGraph
import io.nosyntax.foundation.presentation.navigation.graph.isUtilityScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    viewModel: MainViewModel = viewModel(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    navController: NavHostController = rememberNavController()
) {
    val context = LocalContext.current
    val appConfig by viewModel.appConfigUI.collectAsState()
    val deeplink by viewModel.deeplink.collectAsState()
    val currentNavBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentNavBackStackEntry?.destination?.route ?: ""
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    appConfig?.let { config ->
        val components = config.components

        val content: @Composable () -> Unit = {
            HomeContent(
                context = context,
                appConfig = config,
                deeplink = deeplink,
                coroutineScope = coroutineScope,
                navController = navController,
                currentRoute = currentRoute,
                drawerState = drawerState
            )
        }

        if (components.sideMenu.visible) {
            SideMenu(
                config = components.sideMenu,
                navController = navController,
                currentRoute = currentRoute,
                drawerState = drawerState,
                content = content
            )
        } else {
            content()
        }
    }

    BackHandler(enabled = drawerState.isOpen, onBack = {
        coroutineScope.launch {
            drawerState.close()
        }
    })
}

@Composable
private fun HomeContent(
    context: Context,
    appConfig: AppConfig,
    deeplink: Deeplink,
    coroutineScope: CoroutineScope,
    navController: NavHostController,
    currentRoute: String,
    drawerState: DrawerState
) {
    val components = appConfig.components
    val snackbarHostState = remember { SnackbarHostState() }
    val selectedItem = appConfig.components.sideMenu.items.find {
        it.route == currentRoute
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
                snackbar = { SnackbarComponent(it.visuals.message) }
            )
        },
        topBar = {
            if (components.appBar.visible) {
                val navigationAction = if (isUtilityScreen(currentRoute)) NavigationAction.Back
                else NavigationAction.Menu(enabled = components.sideMenu.visible)

                AppBar(
                    config = components.appBar,
                    title = selectedItem?.label ?: "",
                    navigationAction = navigationAction,
                    onNavigationActionClick = {
                        if (navigationAction is NavigationAction.Menu) {
                            coroutineScope.launch { drawerState.open() }
                        } else {
                            (context.findActivity() as MainActivity).showInterstitial(onAdDismissed = {
                                navController.popBackStack()
                            })
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
                NavigationGraph(
                    appConfig = appConfig,
                    deeplink = deeplink,
                    navController = navController,
                    drawerState = drawerState
                )
            }
        },
    )
}
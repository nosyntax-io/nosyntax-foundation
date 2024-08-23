package io.nosyntax.foundation.presentation.screen.main

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
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import io.nosyntax.foundation.R
import io.nosyntax.foundation.core.extension.getDistinctNavigationItems
import io.nosyntax.foundation.core.extension.isTopLevelRoute
import io.nosyntax.foundation.presentation.component.AppBar
import io.nosyntax.foundation.presentation.component.NavigationBar
import io.nosyntax.foundation.presentation.component.NavigationAction
import io.nosyntax.foundation.domain.model.Deeplink
import io.nosyntax.foundation.domain.model.NavigationItem
import io.nosyntax.foundation.presentation.component.NavigationDrawer
import io.nosyntax.foundation.presentation.component.Snackbar
import io.nosyntax.foundation.domain.model.app_config.AppConfig
import io.nosyntax.foundation.domain.model.app_config.Components
import io.nosyntax.foundation.presentation.navigation.NavigationHost
import io.nosyntax.foundation.presentation.navigation.NavigationManager
import io.nosyntax.foundation.presentation.MainViewModel
import io.nosyntax.foundation.presentation.navigation.rememberNavManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun MainScreen(
    viewModel: MainViewModel = viewModel(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    navManager: NavigationManager = rememberNavManager(),
    deeplink: Deeplink? = null
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)

    viewModel.appConfig.collectAsState().value.response?.let { appConfig ->
        val content: @Composable () -> Unit = {
            MainContent(
                appConfig = appConfig,
                coroutineScope = coroutineScope,
                navManager = navManager,
                drawerState = drawerState,
                deeplink = deeplink
            )
        }

        appConfig.components.navigationDrawer.takeIf {
            it.visible
        }?.let { navDrawerConfig ->
            NavigationDrawer(
                config = navDrawerConfig,
                currentRoute = navManager.currentRoute,
                drawerState = drawerState,
                content = content,
                onItemClick = { item ->
                    navManager.handleNavItemClick(item)
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
    appConfig: AppConfig,
    coroutineScope: CoroutineScope,
    navManager: NavigationManager,
    drawerState: DrawerState,
    deeplink: Deeplink?
) {
    Scaffold(
        snackbarHost = {
            MainSnackbarHost()
        },
        topBar = {
            MainAppBar(
                appConfig = appConfig,
                currentRoute = navManager.currentRoute,
                navigateBack = { navManager.navController.popBackStack() },
                openDrawer = { coroutineScope.launch { drawerState.open() } }
            )
        },
        content = { padding ->
            Box(modifier = Modifier.padding(padding)) {
                NavigationHost(
                    appConfig = appConfig,
                    navController = navManager.navController,
                    drawerState = drawerState,
                    deeplink = deeplink
                )
            }
        },
        bottomBar = {
            MainNavigationBar(
                navBarConfig = appConfig.components.navigationBar,
                currentRoute = navManager.currentRoute,
                onItemClick = { item ->
                    navManager.handleNavItemClick(item)
                }
            )
        }
    )
}

@Composable
private fun MainSnackbarHost() {
    SnackbarHost(
        hostState = remember { SnackbarHostState() },
        snackbar = { Snackbar(it.visuals.message) }
    )
}

@Composable
private fun MainAppBar(
    appConfig: AppConfig,
    currentRoute: String,
    navigateBack: () -> Unit,
    openDrawer: () -> Unit
) {
    if (appConfig.components.appBar.visible) {
        val title = when (currentRoute) {
            "about" -> stringResource(id = R.string.about_us)
            else -> appConfig.getDistinctNavigationItems.find { item ->
                item.route == currentRoute
            }?.label.orEmpty()
        }

        val action = if (!currentRoute.isTopLevelRoute()) {
            NavigationAction.Back { navigateBack() }
        } else {
            NavigationAction.Menu(
                isEnabled = appConfig.components.navigationDrawer.visible
            ) { openDrawer() }
        }

        AppBar(
            config = appConfig.components.appBar,
            title = title,
            navigationAction = action
        )
    }
}

@Composable
private fun MainNavigationBar(
    navBarConfig: Components.NavigationBar,
    currentRoute: String,
    onItemClick: (NavigationItem) -> Unit
) {
    if (navBarConfig.visible && currentRoute.isTopLevelRoute()) {
        NavigationBar(
            config = navBarConfig,
            currentRoute = currentRoute,
            onItemClick = onItemClick
        )
    }
}
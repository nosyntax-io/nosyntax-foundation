package io.nosyntax.foundation.presentation.navigation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import io.nosyntax.foundation.core.Constants
import io.nosyntax.foundation.core.component.Icon
import io.nosyntax.foundation.core.component.Image
import io.nosyntax.foundation.core.utility.Previews
import io.nosyntax.foundation.domain.model.app_config.Components
import io.nosyntax.foundation.presentation.navigation.graph.Navigator
import io.nosyntax.foundation.ui.theme.DynamicTheme
import kotlinx.coroutines.launch

@Composable
fun SideMenu(
    config: Components.SideMenu,
    navController: NavHostController,
    currentRoute: String,
    drawerState: DrawerState,
    content: @Composable () -> Unit
) {
    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = drawerState.isOpen,
        drawerContent = {
            SideMenuContent(
                config = config,
                navController = navController,
                currentRoute = currentRoute,
                drawerState = drawerState
            )
        },
        content = content
    )
}

@Composable
fun SideMenuContent(
    config: Components.SideMenu,
    navController: NavHostController,
    currentRoute: String,
    drawerState: DrawerState
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    val backgroundModifier = when (config.background) {
        Constants.BACKGROUND_NEUTRAL -> Modifier.background(
            color = MaterialTheme.colorScheme.surface
        )
        Constants.BACKGROUND_SOLID -> Modifier.background(
            color = MaterialTheme.colorScheme.primary
        )
        Constants.BACKGROUND_GRADIENT -> Modifier.background(
            brush = Brush.verticalGradient(
                colors = listOf(MaterialTheme.colorScheme.primary, MaterialTheme.colorScheme.secondary)
            )
        )
        else -> Modifier
    }

    ModalDrawerSheet(
        modifier = Modifier
            .padding(end = 100.dp)
            .clip(shape = RoundedCornerShape(topEnd = 15.dp, bottomEnd = 15.dp))
            .then(backgroundModifier),
        drawerContainerColor = Color.Transparent,
        drawerContentColor = Color.Transparent
    ) {
        SideMenuHeader(config = config)
        Spacer(modifier = Modifier.height(20.dp))
        LazyColumn {
            items(config.items.size) { index ->
                val item = config.items[index]

                SideMenuItem(
                    config = config,
                    currentRoute = currentRoute,
                    item = item,
                    onClick = {
                        val navigator = Navigator(context, navController)

                        if (setOf("browser", "mail", "dial", "sms").any { item.type == it }) {
                            navigator.open(type = item.type, action = item.action!!)
                        } else {
                            navigator.navigate(currentRoute = currentRoute, route = item.route!!)
                        }

                        coroutineScope.launch {
                            drawerState.close()
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun SideMenuHeader(config: Components.SideMenu, headerHeight: Dp = 150.dp) {
    if (config.header.visible) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .height(headerHeight)
                .padding(start = 20.dp, top = 20.dp, end = 20.dp)
                .clip(shape = MaterialTheme.shapes.large),
            source = config.header.image
        )
    }
}

@Composable
fun SideMenuItem(
    config: Components.SideMenu,
    currentRoute: String,
    item: Components.SideMenu.Item,
    onClick: () -> Unit
) {
    val isSelected = currentRoute == item.route
    val (containerColor, contentColor) = if (config.background == Constants.BACKGROUND_NEUTRAL) {
        MaterialTheme.colorScheme.secondary to MaterialTheme.colorScheme.onSurface
    } else {
        MaterialTheme.colorScheme.onPrimary to MaterialTheme.colorScheme.onPrimary
    }

    NavigationDrawerItem(
        modifier = Modifier
            .height(50.dp)
            .padding(horizontal = 20.dp),
        selected = isSelected,
        onClick = onClick,
        shape = MaterialTheme.shapes.large,
        colors = NavigationDrawerItemDefaults.colors(
            selectedContainerColor = containerColor.copy(alpha = .1f),
            unselectedContainerColor = Color.Transparent,
            selectedTextColor = contentColor,
            selectedIconColor = contentColor,
            unselectedTextColor = contentColor.copy(alpha = 0.8f),
            unselectedIconColor = contentColor.copy(alpha = 0.8f)
        ),
        label = {
            if (item.label != null) {
                Text(
                    text = item.label,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        },
        icon = {
            if (item.icon != null) {
                Icon(
                    modifier = Modifier.size(22.dp),
                    source = item.icon
                )
            }
        }
    )
}

@Previews
@Composable
private fun SideMenuPreview() {
    DynamicTheme {
        val navController = rememberNavController()
        val drawerState = rememberDrawerState(DrawerValue.Open)
        val currentRoute by remember { mutableStateOf("1") }

        val items = listOf(
            Components.SideMenu.Item("1", type = "webview",  "Home", "https://img.icons8.com/fluency-systems-filled/96/shopping-cart.png"),
            Components.SideMenu.Item("2", type = "webview",  "Store", "https://img.icons8.com/fluency-systems-filled/96/shopping-cart.png"),
            Components.SideMenu.Item("3", type = "webview",  "Blog", "https://img.icons8.com/fluency-systems-filled/96/medium-logo.png"),
            Components.SideMenu.Item("4", type = "settings",  "Settings", "https://img.icons8.com/fluency-systems-filled/96/gear.png"),
            Components.SideMenu.Item("5", type = "about",  "About", "https://img.icons8.com/fluency-systems-filled/96/user-male-circle.png")
        )

        SideMenu(
            config = Components.SideMenu(
                visible = true,
                background = Constants.BACKGROUND_NEUTRAL,
                header = Components.SideMenu.Header(
                    visible = true,
                    image = "https://via.placeholder.com/700x400"
                ),
                items = items
            ),
            navController = navController,
            currentRoute = currentRoute,
            drawerState = drawerState,
            content = { }
        )
    }
}
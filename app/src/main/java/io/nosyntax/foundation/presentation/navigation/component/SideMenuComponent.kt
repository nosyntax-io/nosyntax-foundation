package io.nosyntax.foundation.presentation.navigation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import io.nosyntax.foundation.core.Constants
import io.nosyntax.foundation.core.component.Icon
import io.nosyntax.foundation.core.component.Image
import io.nosyntax.foundation.core.utility.Utilities.setColorContrast
import io.nosyntax.foundation.domain.model.NavigationItem
import io.nosyntax.foundation.domain.model.app_config.SideMenuConfig
import io.nosyntax.foundation.presentation.navigation.graph.NavigationActions
import io.nosyntax.foundation.ui.theme.DynamicTheme
import kotlinx.coroutines.launch

@Composable
fun SideMenu(
    sideMenuConfig: SideMenuConfig,
    navController: NavHostController,
    currentRoute: String,
    navigationItems: List<NavigationItem>,
    drawerState: DrawerState,
    content: @Composable () -> Unit
) {
    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = drawerState.isOpen,
        drawerContent = {
            SideMenuContent(
                sideMenuConfig = sideMenuConfig,
                navController = navController,
                currentRoute = currentRoute,
                navigationItems = navigationItems,
                drawerState = drawerState
            )
        },
        content = content
    )
}

@Composable
fun SideMenuContent(
    sideMenuConfig: SideMenuConfig,
    navController: NavHostController,
    currentRoute: String,
    navigationItems: List<NavigationItem>,
    drawerState: DrawerState
) {
    val coroutineScope = rememberCoroutineScope()

    val containerColor = when (sideMenuConfig.background) {
        Constants.BACKGROUND_NEUTRAL -> Modifier.background(
            color = MaterialTheme.colorScheme.surface
        )
        Constants.BACKGROUND_SOLID -> Modifier.background(
            color = MaterialTheme.colorScheme.primary
        )
        Constants.BACKGROUND_GRADIENT -> {
            Modifier.background(
                Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.primary,
                        MaterialTheme.colorScheme.secondary
                    )
                )
            )
        }
        else -> Modifier
    }

    ModalDrawerSheet(
        modifier = Modifier
            .padding(end = 90.dp)
            .clip(shape = RoundedCornerShape(topEnd = 15.dp, bottomEnd = 15.dp))
            .then(other = containerColor),
        drawerContainerColor = Color.Transparent,
        content = {
            SideMenuHeader(sideMenuConfig = sideMenuConfig)
            Spacer(modifier = Modifier.height(20.dp))
            LazyColumn(content = {
                items(navigationItems.size) { index ->
                    val item = navigationItems[index]
                    when (item.type) {
                        "divider" -> {
                            val dividerColor = MaterialTheme.colorScheme.let {
                                if (sideMenuConfig.background == Constants.BACKGROUND_NEUTRAL)
                                    setColorContrast(isSystemInDarkTheme(), MaterialTheme.colorScheme.surface)
                                else Color.White.copy(alpha = .6f)
                            }
                            Divider(
                                modifier = Modifier.padding(vertical = 7.dp),
                                thickness = 1.dp,
                                color = dividerColor
                            )
                        }
                        else -> {
                            SideMenuItem(
                                sideMenuConfig = sideMenuConfig,
                                currentRoute = currentRoute,
                                item = item,
                                onClick = {
                                    NavigationActions(navController).navigateTo(
                                        currentRoute = currentRoute,
                                        route = item.id
                                    )
                                    coroutineScope.launch {
                                        drawerState.close()
                                    }
                                }
                            )
                        }
                    }
                }
            })
        }
    )
}

@Composable
fun SideMenuHeader(sideMenuConfig: SideMenuConfig, headerHeight: Dp = 150.dp) {
    val header = sideMenuConfig.header
    if (header.display) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(headerHeight)
                .padding(start = 20.dp, top = 20.dp, end = 20.dp)
                .clip(shape = MaterialTheme.shapes.large),
            content = {
                Image(
                    modifier = Modifier.fillMaxWidth(),
                    source = header.image
                )
            }
        )
    }
}

@Composable
fun SideMenuItem(
    sideMenuConfig: SideMenuConfig,
    currentRoute: String,
    item: NavigationItem,
    onClick: () -> Unit
) {
    val contentColor = MaterialTheme.colorScheme.let {
        if (sideMenuConfig.background == Constants.BACKGROUND_NEUTRAL)
            it.onSurface else it.onPrimary
    }

    val isSelected = currentRoute == item.id
    val unselectedColor = contentColor.copy(alpha = 0.8f)

    NavigationDrawerItem(
        modifier = Modifier
            .height(50.dp)
            .padding(horizontal = 20.dp),
        selected = currentRoute == item.id,
        onClick = onClick,
        shape = MaterialTheme.shapes.large,
        colors = NavigationDrawerItemDefaults.colors(
            selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
            unselectedContainerColor = Color.Transparent,
            selectedTextColor = contentColor,
            selectedIconColor = contentColor,
            unselectedTextColor = if (isSelected) contentColor else unselectedColor,
            unselectedIconColor = if (isSelected) contentColor else unselectedColor,
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

@Preview
@Composable
private fun SideMenuPreview() {
    DynamicTheme {
        val navController = rememberNavController()
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Open)
        val currentRoute by remember { mutableStateOf("item1") }

        /*SideMenu(
            sideMenuConfig = SideMenuConfig(
                display = true,
                background = Constants.BACKGROUND_NEUTRAL,
                header = SideMenuConfig.Header(
                    display = true,
                    image = "https://via.placeholder.com/700x400"
                )
            ),
            navController = navController,
            currentRoute = currentRoute,
            navigationItems = generateMockNavigationItems(
                itemCount = 10
            ),
            drawerState = drawerState,
            content = { }
        )*/
    }
}
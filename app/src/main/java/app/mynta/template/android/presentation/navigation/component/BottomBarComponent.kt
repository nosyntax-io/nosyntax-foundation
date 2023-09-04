package app.mynta.template.android.presentation.navigation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import app.mynta.template.android.core.Constants
import app.mynta.template.android.core.components.DynamicIcon
import app.mynta.template.android.domain.model.app_config.BottomBarConfig
import app.mynta.template.android.domain.model.NavigationItem
import app.mynta.template.android.presentation.navigation.graph.Roles
import app.mynta.template.android.presentation.navigation.graph.Routes
import app.mynta.template.android.presentation.navigation.graph.Types
import app.mynta.template.android.ui.theme.DynamicTheme
import kotlinx.coroutines.CoroutineScope

@Composable
fun BottomBar(
    bottomBarConfig: BottomBarConfig,
    coroutineScope: CoroutineScope,
    navController: NavHostController,
    currentRoute: String,
    navigationItems: List<NavigationItem>
) {
    var isMoreOptionsOpened by rememberSaveable { mutableStateOf(false) }

    if (isMoreOptionsOpened) {
        MoreOptionsBottomSheet(
            coroutineScope = coroutineScope,
            navController = navController,
            navigationItems = navigationItems.filter { item ->
                item.type == "core"
            },
            onDismiss = {
                isMoreOptionsOpened = false
            }
        )
    }

    val containerColor = when (bottomBarConfig.background) {
        Constants.BACKGROUND_NEUTRAL -> Modifier.background(color = MaterialTheme.colorScheme.surface)
        Constants.BACKGROUND_SOLID -> Modifier.background(color = MaterialTheme.colorScheme.primary)
        Constants.BACKGROUND_GRADIENT -> Modifier.background(
            Brush.horizontalGradient(
                colors = listOf(
                    MaterialTheme.colorScheme.primary,
                    MaterialTheme.colorScheme.secondary
                )
            )
        )
        else -> Modifier
    }

    val navBarHeight = if (bottomBarConfig.label == Constants.LABEL_HIDDEN) {
        Modifier.height(55.dp)
    } else {
        Modifier
    }

    NavigationBar(
        modifier = Modifier
            .then(navBarHeight)
            .then(containerColor)
            .padding(horizontal = 15.dp),
        containerColor = Color.Transparent
    ) {
        navigationItems.filter { it.type == Types.TYPE_REGULAR }.plus(
            NavigationItem(id = "more", role = Roles.ROLE_MORE, icon = "https://img.icons8.com/?size=512&id=61873&format=png")
        ).forEach { item ->
            BottomBarNavigationItem(
                bottomBarConfig = bottomBarConfig,
                currentRoute = currentRoute,
                item = item,
                onClick = {
                    when (item.id) {
                        "more" -> isMoreOptionsOpened = true
                        else -> navController.navigate(route = item.id)
                    }
                }
            )
        }
    }
}

@Composable
fun RowScope.BottomBarNavigationItem(
    bottomBarConfig: BottomBarConfig,
    currentRoute: String,
    item: NavigationItem,
    onClick: () -> Unit
) {
    NavigationBarItem(
        selected = currentRoute == item.id,
        alwaysShowLabel = bottomBarConfig.label != Constants.LABEL_SELECTED,
        colors = bottomBarNavigationItemColors(bottomBarConfig = bottomBarConfig),
        label = {
            if (bottomBarConfig.label != Constants.LABEL_HIDDEN) {
                Text(
                    modifier = Modifier,
                    text = item.label,
                    style = MaterialTheme.typography.labelSmall
                )
            }
        },
        icon = {
            DynamicIcon(
                modifier = Modifier.size(22.dp),
                source = item.icon
            )
        },
        onClick = onClick
    )
}

@Composable
fun bottomBarNavigationItemColors(bottomBarConfig: BottomBarConfig): NavigationBarItemColors {
    val contentColor = when (bottomBarConfig.background) {
        Constants.BACKGROUND_NEUTRAL -> MaterialTheme.colorScheme.onSurface
        else -> MaterialTheme.colorScheme.surface
    }
    return NavigationBarItemDefaults.colors(
        selectedTextColor = contentColor,
        selectedIconColor = contentColor,
        unselectedTextColor = contentColor,
        unselectedIconColor = contentColor,
        indicatorColor = MaterialTheme.colorScheme.primaryContainer
    )
}

@Preview
@Composable
fun BottomBarPreview() {
    DynamicTheme {
        val coroutineScope = rememberCoroutineScope()
        val navController = rememberNavController()
        val currentRoute = Routes.ROUTE_HOME

        val placeholderIcon = "https://img.icons8.com/?size=512&id=99291&format=png"
        val navigationItems = listOf(
            NavigationItem("item1", "type", "Item 1", placeholderIcon, type = "regular"),
            NavigationItem("item2", "type", "Item 2", placeholderIcon, type = "regular"),
            NavigationItem("item3", "type", "Item 3", placeholderIcon, type = "regular"),
            NavigationItem("item4", "type", "Item 4", placeholderIcon, type = "regular")
        )

        BottomBar(
            bottomBarConfig = BottomBarConfig(
                display = true,
                background = Constants.BACKGROUND_NEUTRAL,
                label = Constants.LABEL_ALWAYS
            ),
            coroutineScope = coroutineScope,
            navController = navController,
            currentRoute = currentRoute,
            navigationItems = navigationItems
        )
    }
}
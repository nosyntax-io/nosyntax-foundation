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
import app.mynta.template.android.presentation.navigation.graph.Routes
import app.mynta.template.android.ui.theme.DynamicTheme

@Composable
fun BottomBar(
    bottomBarConfig: BottomBarConfig,
    navController: NavHostController,
    currentRoute: String,
    navigationItems: List<NavigationItem>
) {
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
        navigationItems.forEach { item ->
            BottomBarNavigationItem(
                bottomBarConfig = bottomBarConfig,
                navController = navController,
                currentRoute = currentRoute,
                item = item
            )
        }
    }
}

@Composable
fun RowScope.BottomBarNavigationItem(
    bottomBarConfig: BottomBarConfig,
    navController: NavHostController,
    currentRoute: String,
    item: NavigationItem
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
        onClick = {
            navController.navigate(route = item.id)
        }
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
        val navController = rememberNavController()
        val currentRoute = Routes.ROUTE_HOME

        val placeholderIcon = "https://img.icons8.com/?size=512&id=99291&format=png"
        val navigationItems = listOf(
            NavigationItem("item1", "type", "Item 1", placeholderIcon),
            NavigationItem("item2", "type", "Item 2", placeholderIcon),
            NavigationItem("item3", "type", "Item 3", placeholderIcon),
            NavigationItem("item4", "type", "Item 4", placeholderIcon)
        )

        BottomBar(
            bottomBarConfig = BottomBarConfig(
                display = true,
                background = Constants.BACKGROUND_NEUTRAL,
                label = Constants.LABEL_ALWAYS
            ),
            navController = navController,
            currentRoute = currentRoute,
            navigationItems = navigationItems)
    }
}
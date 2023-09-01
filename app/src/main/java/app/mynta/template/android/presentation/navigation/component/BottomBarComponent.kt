package app.mynta.template.android.presentation.navigation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import app.mynta.template.android.domain.model.app_config.BottomBarConfig
import app.mynta.template.android.domain.model.NavigationItem
import app.mynta.template.android.ui.theme.DynamicTheme
import coil.compose.rememberAsyncImagePainter

@Composable
fun BottomBar(
    bottomBarConfig: BottomBarConfig,
    navController: NavHostController,
    currentRoute: String,
    navigationItems: List<NavigationItem>
) {
    val containerColor = when (bottomBarConfig.background) {
        "neutral" -> Modifier.background(color = MaterialTheme.colorScheme.surface)
        "solid" -> Modifier.background(color = MaterialTheme.colorScheme.primary)
        "gradient" -> Modifier.background(
            Brush.horizontalGradient(
                colors = listOf(
                    MaterialTheme.colorScheme.primary,
                    MaterialTheme.colorScheme.secondary
                )
            )
        )
        else -> Modifier
    }

    val navBarHeight = if (bottomBarConfig.label == "hidden") {
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
        alwaysShowLabel = bottomBarConfig.label != "selected",
        colors = bottomBarNavigationItemColors(bottomBarConfig = bottomBarConfig),
        label = {
            if (bottomBarConfig.label != "hidden") {
                Text(
                    modifier = Modifier,
                    text = item.label,
                    style = MaterialTheme.typography.labelSmall
                )
            }
        },
        icon = {
            Icon(
                modifier = Modifier.size(22.dp),
                painter = rememberAsyncImagePainter(
                    model = item.icon,
                    contentScale = ContentScale.Crop,
                    filterQuality = FilterQuality.High
                ),
                contentDescription = null
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
        "neutral" -> MaterialTheme.colorScheme.onSurface
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
        val currentRoute = "home"

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
                background = "neutral",
                label = "always"
            ),
            navController = navController,
            currentRoute = currentRoute,
            navigationItems = navigationItems)
    }
}
package io.nosyntax.template.android.presentation.navigation.component

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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import io.nosyntax.template.android.core.Constants
import io.nosyntax.template.android.core.component.DynamicIcon
import io.nosyntax.template.android.domain.model.NavigationItem
import io.nosyntax.template.android.domain.model.app_config.BottomBarConfig
import io.nosyntax.template.android.presentation.navigation.graph.NavigationActions
import io.nosyntax.template.android.ui.theme.DynamicTheme

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
                currentRoute = currentRoute,
                item = item,
                onClick = {
                    NavigationActions(navController).navigateTo(
                        currentRoute = currentRoute,
                        route = item.label?.lowercase() ?: ""
                    )
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
        selected = currentRoute == item.route.toString(),
        alwaysShowLabel = bottomBarConfig.label != Constants.LABEL_SELECTED,
        colors = bottomBarNavigationItemColors(bottomBarConfig = bottomBarConfig),
        label = {
            if (bottomBarConfig.label != Constants.LABEL_HIDDEN) {
                Text(
                    modifier = Modifier,
                    text = item.label ?: "",
                    style = MaterialTheme.typography.labelSmall,
                    textAlign = TextAlign.Center,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
            }
        },
        icon = {
            if (item.icon != null) {
                DynamicIcon(
                    modifier = Modifier.size(22.dp),
                    source = item.icon
                )
            }
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
        /*BottomBar(
            bottomBarConfig = BottomBarConfig(
                display = true,
                background = Constants.BACKGROUND_NEUTRAL,
                label = Constants.LABEL_ALWAYS
            ),
            navController = rememberNavController(),
            currentRoute = "item1",
            navigationItems = generateMockNavigationItems(itemCount = 4)
        )*/
    }
}
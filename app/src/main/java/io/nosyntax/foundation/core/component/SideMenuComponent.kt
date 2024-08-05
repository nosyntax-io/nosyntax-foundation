package io.nosyntax.foundation.core.component

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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.nosyntax.foundation.core.Constants
import io.nosyntax.foundation.core.utility.AppConfigProvider
import io.nosyntax.foundation.core.utility.Previews
import io.nosyntax.foundation.domain.model.app_config.AppConfig
import io.nosyntax.foundation.domain.model.app_config.Components
import io.nosyntax.foundation.ui.theme.DynamicTheme

@Composable
fun SideMenu(
    config: Components.SideMenu,
    currentRoute: String,
    drawerState: DrawerState,
    onItemClick: (Components.SideMenu.Item) -> Unit,
    content: @Composable () -> Unit
) {
    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = drawerState.isOpen,
        content = content,
        drawerContent = {
            SideMenuContent(
                config = config,
                currentRoute = currentRoute,
                onItemClick = onItemClick
            )
        }
    )
}

@Composable
fun SideMenuContent(
    config: Components.SideMenu,
    currentRoute: String,
    onItemClick: (Components.SideMenu.Item) -> Unit,
) {
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

                // TODO: Add support for dividers
                SideMenuItem(
                    config = config,
                    currentRoute = currentRoute,
                    item = item,
                    onClick = { onItemClick(item) }
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
            selectedContainerColor = containerColor.copy(alpha = .12f),
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
private fun SideMenuPreview(
    @PreviewParameter(AppConfigProvider::class) appConfig: AppConfig
) {
    DynamicTheme {
        SideMenu(
            config = appConfig.components.sideMenu,
            currentRoute = "web-000",
            drawerState = DrawerState(DrawerValue.Open),
            onItemClick = { },
            content = { }
        )
    }
}
package io.nosyntax.foundation.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.HorizontalDivider
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
import io.nosyntax.foundation.core.constant.Constants
import io.nosyntax.foundation.core.component.AsyncIcon
import io.nosyntax.foundation.core.component.AsyncImage
import io.nosyntax.foundation.core.util.AppConfigProvider
import io.nosyntax.foundation.core.util.Previews
import io.nosyntax.foundation.domain.model.NavigationItem
import io.nosyntax.foundation.domain.model.app_config.AppConfig
import io.nosyntax.foundation.domain.model.app_config.Components
import io.nosyntax.foundation.presentation.theme.FoundationTheme

@Composable
fun NavigationDrawer(
    config: Components.NavigationDrawer,
    currentRoute: String,
    drawerState: DrawerState,
    onItemClick: (NavigationItem) -> Unit,
    content: @Composable () -> Unit
) {
    ModalNavigationDrawer(
        drawerContent = {
            NavigationDrawerContent(
                config = config,
                currentRoute = currentRoute,
                onItemClick = onItemClick
            )
        },
        drawerState = drawerState,
        gesturesEnabled = drawerState.isOpen,
        content = content
    )
}

@Composable
private fun NavigationDrawerContent(
    config: Components.NavigationDrawer,
    currentRoute: String,
    onItemClick: (NavigationItem) -> Unit,
) {
    val backgroundModifier = when (config.background) {
        Constants.Color.NEUTRAL -> Modifier.background(
            color = MaterialTheme.colorScheme.surface
        )
        Constants.Color.SOLID -> Modifier.background(
            color = MaterialTheme.colorScheme.primary
        )
        Constants.Color.GRADIENT -> Modifier.background(
            brush = Brush.verticalGradient(
                colors = listOf(
                    MaterialTheme.colorScheme.primary,
                    MaterialTheme.colorScheme.secondary
                )
            )
        )
        else -> Modifier
    }

    ModalDrawerSheet(
        modifier = Modifier
            .padding(end = 100.dp)
            .clip(shape = RoundedCornerShape(
                topEnd = 15.dp, bottomEnd = 15.dp
            ))
            .then(backgroundModifier),
        drawerContainerColor = Color.Transparent,
        drawerContentColor = Color.Transparent
    ) {
        LazyColumn {
            item {
                NavigationDrawerHeader(config = config)
                Spacer(modifier = Modifier.height(20.dp))
            }
            items(items = config.items) { item ->
                when (item.type) {
                    Constants.Navigation.DIVIDER -> {
                        NavigationDrawerDivider(
                            config = config
                        )
                    }
                    else -> {
                        NavigationDrawerItem(
                            config = config,
                            currentRoute = currentRoute,
                            item = item,
                            onClick = { onItemClick(item) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun NavigationDrawerHeader(
    config: Components.NavigationDrawer,
    height: Dp = 150.dp
) {
    if (config.header.visible) {
        AsyncImage(
            url = config.header.image,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(height)
                .padding(start = 20.dp, top = 20.dp, end = 20.dp)
                .clip(shape = MaterialTheme.shapes.medium)
        )
    }
}

@Composable
private fun NavigationDrawerDivider(
    config: Components.NavigationDrawer
) {
    val dividerColor = when (config.background) {
        Constants.Color.NEUTRAL -> MaterialTheme.colorScheme.outlineVariant
        else -> MaterialTheme.colorScheme.onPrimary.copy(alpha = .3f)
    }

    HorizontalDivider(
        modifier = Modifier.padding(vertical = 7.dp),
        color = dividerColor
    )
}

@Composable
private fun NavigationDrawerItem(
    config: Components.NavigationDrawer,
    currentRoute: String,
    item: NavigationItem,
    onClick: () -> Unit
) {
    val containerColor = when (config.background) {
        Constants.Color.NEUTRAL -> MaterialTheme.colorScheme.secondary.copy(alpha = .12f)
        else -> MaterialTheme.colorScheme.onPrimary.copy(alpha = .12f)
    }

    val contentColor = when (config.background) {
        Constants.Color.NEUTRAL -> MaterialTheme.colorScheme.onSurface
        else -> MaterialTheme.colorScheme.onPrimary
    }

    NavigationDrawerItem(
        selected = currentRoute == item.route,
        onClick = onClick,
        modifier = Modifier
            .height(50.dp)
            .padding(horizontal = 20.dp),
        shape = MaterialTheme.shapes.medium,
        colors = NavigationDrawerItemDefaults.colors(
            selectedContainerColor = containerColor,
            unselectedContainerColor = Color.Transparent,
            selectedIconColor = contentColor,
            unselectedIconColor = contentColor.copy(alpha = .8f),
            selectedTextColor = contentColor,
            unselectedTextColor = contentColor.copy(alpha = .8f)
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
                AsyncIcon(
                    url = item.icon,
                    contentDescription = null,
                    modifier = Modifier.size(22.dp)
                )
            }
        }
    )
}

@Previews
@Composable
private fun NavigationDrawerPreview(
    @PreviewParameter(AppConfigProvider::class) appConfig: AppConfig
) {
    FoundationTheme {
        NavigationDrawer(
            config = appConfig.components.navigationDrawer,
            currentRoute = "route",
            drawerState = DrawerState(DrawerValue.Open),
            onItemClick = { },
            content = { }
        )
    }
}
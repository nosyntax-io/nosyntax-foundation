package io.nosyntax.foundation.core.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import io.nosyntax.foundation.core.Constants
import io.nosyntax.foundation.domain.model.NavigationItem
import io.nosyntax.foundation.domain.model.app_config.Components

@Composable
fun NavigationBar(
    config: Components.NavigationBar,
    currentRoute: String,
    onItemClick: (NavigationItem) -> Unit
) {
    val backgroundModifier = when (config.background) {
        Constants.BACKGROUND_NEUTRAL -> Modifier.background(
            color = MaterialTheme.colorScheme.surface
        )
        Constants.BACKGROUND_SOLID -> Modifier.background(
            color = MaterialTheme.colorScheme.primary
        )
        Constants.BACKGROUND_GRADIENT -> Modifier.background(
            brush = Brush.horizontalGradient(
                colors = listOf(MaterialTheme.colorScheme.primary, MaterialTheme.colorScheme.secondary)
            )
        )
        else -> Modifier
    }

    val heightModifier = if (config.label == Constants.LABEL_HIDDEN) {
        Modifier.height(55.dp)
    } else {
        Modifier
    }

    NavigationBar(
        modifier = Modifier
            .then(heightModifier)
            .then(backgroundModifier)
            .padding(horizontal = 15.dp),
        containerColor = Color.Transparent
    ) {
        config.items.forEach { item ->
            NavigationBarItem(
                config = config,
                currentRoute = currentRoute,
                item = item,
                onClick = { onItemClick(item) }
            )
        }
    }
}

@Composable
fun RowScope.NavigationBarItem(
    config: Components.NavigationBar,
    currentRoute: String,
    item: NavigationItem,
    onClick: () -> Unit
) {
    val (indicatorColor, contentColor) = if (config.background == Constants.BACKGROUND_NEUTRAL) {
        MaterialTheme.colorScheme.secondary to MaterialTheme.colorScheme.onSurface
    } else {
        MaterialTheme.colorScheme.onPrimary to MaterialTheme.colorScheme.onPrimary
    }

    NavigationBarItem(
        selected = currentRoute == item.route,
        onClick = onClick,
        alwaysShowLabel = config.label != Constants.LABEL_SELECTED,
        colors = NavigationBarItemDefaults.colors(
            selectedIconColor = contentColor,
            selectedTextColor = contentColor,
            indicatorColor =  indicatorColor.copy(alpha = 0.12f),
            unselectedIconColor = contentColor.copy(alpha = 0.8f),
            unselectedTextColor = contentColor.copy(alpha = 0.8f),
        ),
        label = {
            if (config.label != Constants.LABEL_HIDDEN) {
                Text(
                    modifier = Modifier,
                    text = item.label.orEmpty(),
                    style = MaterialTheme.typography.labelSmall,
                    textAlign = TextAlign.Center,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
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
package io.nosyntax.foundation.presentation.main.component

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
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import io.nosyntax.foundation.core.Constants
import io.nosyntax.foundation.core.component.AsyncIcon
import io.nosyntax.foundation.core.utility.AppConfigProvider
import io.nosyntax.foundation.core.utility.Previews
import io.nosyntax.foundation.domain.model.NavigationItem
import io.nosyntax.foundation.domain.model.app_config.AppConfig
import io.nosyntax.foundation.domain.model.app_config.Components
import io.nosyntax.foundation.ui.theme.DynamicTheme

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
                colors = listOf(
                    MaterialTheme.colorScheme.primary,
                    MaterialTheme.colorScheme.secondary
                )
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
private fun RowScope.NavigationBarItem(
    config: Components.NavigationBar,
    currentRoute: String,
    item: NavigationItem,
    onClick: () -> Unit
) {
    val indicatorColor = if (config.background == Constants.BACKGROUND_NEUTRAL) {
        MaterialTheme.colorScheme.secondary.copy(alpha = .12f)
    } else {
        MaterialTheme.colorScheme.onPrimary.copy(alpha = .12f)
    }

    val contentColor = if (config.background == Constants.BACKGROUND_NEUTRAL) {
        MaterialTheme.colorScheme.onSurface
    } else {
        MaterialTheme.colorScheme.onPrimary
    }

    NavigationBarItem(
        selected = currentRoute == item.route,
        onClick = onClick,
        alwaysShowLabel = config.label != Constants.LABEL_SELECTED,
        colors = NavigationBarItemDefaults.colors(
            selectedIconColor = contentColor,
            selectedTextColor = contentColor,
            indicatorColor = indicatorColor,
            unselectedIconColor = contentColor.copy(alpha = .8f),
            unselectedTextColor = contentColor.copy(alpha = .8f),
        ),
        label = {
            if (item.label != null && config.label != Constants.LABEL_HIDDEN) {
                Text(
                    text = item.label,
                    modifier = Modifier,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    style = MaterialTheme.typography.labelMedium.copy(
                        textAlign = TextAlign.Center
                    )
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
private fun NavigationBarPreview(
    @PreviewParameter(AppConfigProvider::class) config: AppConfig
) {
    DynamicTheme {
        NavigationBar(
            config = config.components.navigationBar,
            currentRoute = "web-000",
            onItemClick = { }
        )
    }
}
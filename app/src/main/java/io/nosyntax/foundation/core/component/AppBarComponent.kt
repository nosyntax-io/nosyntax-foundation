package io.nosyntax.foundation.core.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.nosyntax.foundation.R
import io.nosyntax.foundation.core.Constants
import io.nosyntax.foundation.domain.model.app_config.Components
import io.nosyntax.foundation.ui.theme.DynamicTheme

sealed class NavigationAction {
    data class Menu(val enabled: Boolean): NavigationAction()
    data object Back: NavigationAction()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    config: Components.AppBar,
    title: String,
    navigationAction: NavigationAction,
    onNavigationActionClick: () -> Unit
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
                listOf(MaterialTheme.colorScheme.primary, MaterialTheme.colorScheme.secondary)
            )
        )
        else -> Modifier
    }

    val contentColor = if (config.background == Constants.BACKGROUND_NEUTRAL) {
        MaterialTheme.colorScheme.onSurface
    } else {
        MaterialTheme.colorScheme.onPrimary
    }

    val appBarModifier = Modifier
        .height(50.dp)
        .then(backgroundModifier)

    val appBarColors = TopAppBarDefaults.topAppBarColors(
        containerColor = Color.Transparent,
        titleContentColor = contentColor,
        navigationIconContentColor = contentColor,
        actionIconContentColor = contentColor
    )

    if (config.title.alignment == Constants.ALIGNMENT_CENTER) {
        CenterAlignedTopAppBar(
            modifier = appBarModifier,
            colors = appBarColors,
            title = { AppBarTitle(config, title) },
            navigationIcon = {
                AppBarNavigationIcon(navigationAction, onNavigationActionClick)
            }
        )
    } else {
        TopAppBar(
            modifier = appBarModifier,
            colors = appBarColors,
            title = { AppBarTitle(config, title) },
            navigationIcon = {
                AppBarNavigationIcon(navigationAction, onNavigationActionClick)
            }
        )
    }
}

@Composable
fun AppBarTitle(config: Components.AppBar, title: String) {
    if (config.title.visible) {
        Box(
            modifier = Modifier.fillMaxHeight(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Composable
fun AppBarNavigationIcon(
    navigationAction: NavigationAction,
    onNavigationActionClick: () -> Unit
) {
    val navigationIconResId = when (navigationAction) {
        is NavigationAction.Back -> R.drawable.icon_arrow_left_filled
        is NavigationAction.Menu -> if (navigationAction.enabled) {
            R.drawable.icon_menu_filled
        } else {
            null
        }
    }

    navigationIconResId?.let {
        AppBarActionIcon(
            icon = painterResource(id = it),
            onClick = onNavigationActionClick
        )
    }
}

@Composable
fun AppBarActionIcon(icon: Painter, onClick: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxHeight(),
        contentAlignment = Alignment.Center
    ) {
        ClickableIcon(
            modifier = Modifier.size(30.dp),
            source = icon,
            onClick = onClick
        )
    }
}

@Preview
@Composable
fun AppBarPreview() {
    DynamicTheme {
        AppBar(
            config = Components.AppBar(
                visible = true,
                background = Constants.BACKGROUND_SOLID,
                title = Components.AppBar.Title(
                    visible = true,
                    alignment = Constants.ALIGNMENT_CENTER
                )
            ),
            title = stringResource(id = R.string.app_name),
            navigationAction = NavigationAction.Menu(enabled = true),
            onNavigationActionClick = { }
        )
    }
}
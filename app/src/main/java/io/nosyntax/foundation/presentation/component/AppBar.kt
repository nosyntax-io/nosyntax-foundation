package io.nosyntax.foundation.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.nosyntax.foundation.R
import io.nosyntax.foundation.core.constant.Constants
import io.nosyntax.foundation.domain.model.app_config.Components
import io.nosyntax.foundation.presentation.theme.FoundationTheme

sealed class NavigationAction {
    abstract fun onClick()

    data class Menu(val isEnabled: Boolean, val clickAction: () -> Unit) : NavigationAction() {
        override fun onClick() = clickAction()
    }

    data class Back(val clickAction: () -> Unit) : NavigationAction() {
        override fun onClick() = clickAction()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    config: Components.AppBar,
    title: String,
    navigationAction: NavigationAction
) {
    val backgroundModifier = when (config.background) {
        Constants.COLOR_NEUTRAL -> Modifier.background(
            color = MaterialTheme.colorScheme.surface
        )
        Constants.COLOR_SOLID -> Modifier.background(
            color = MaterialTheme.colorScheme.primary
        )
        Constants.COLOR_GRADIENT -> Modifier.background(
            brush = Brush.horizontalGradient(
                colors = listOf(
                    MaterialTheme.colorScheme.primary,
                    MaterialTheme.colorScheme.secondary
                )
            )
        )
        else -> Modifier
    }

    val contentColor = if (config.background == Constants.COLOR_NEUTRAL) {
        MaterialTheme.colorScheme.onSurface
    } else {
        MaterialTheme.colorScheme.onPrimary
    }

    val appBarModifier = Modifier
        .height(50.dp)
        .then(backgroundModifier)

    val appBarColors = TopAppBarDefaults.topAppBarColors(
        containerColor = Color.Transparent,
        navigationIconContentColor = contentColor,
        titleContentColor = contentColor,
        actionIconContentColor = contentColor
    )

    if (config.title.alignment == Constants.ALIGNMENT_CENTER) {
        CenterAlignedTopAppBar(
            title = { AppBarTitle(config, title) },
            modifier = appBarModifier,
            navigationIcon = { AppBarNavigationIcon(navigationAction) },
            colors = appBarColors
        )
    } else {
        TopAppBar(
            title = { AppBarTitle(config, title) },
            modifier = appBarModifier,
            navigationIcon = { AppBarNavigationIcon(navigationAction) },
            colors = appBarColors
        )
    }
}

@Composable
private fun AppBarTitle(
    config: Components.AppBar,
    title: String
) {
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
private fun AppBarNavigationIcon(navigationAction: NavigationAction) {
    val iconPainter = when (navigationAction) {
        is NavigationAction.Back -> painterResource(id = R.drawable.icon_arrow_left_filled)
        is NavigationAction.Menu -> if (navigationAction.isEnabled) {
            painterResource(id = R.drawable.icon_menu_filled)
        } else null
    }

    iconPainter?.let { icon ->
        Box(
            modifier = Modifier.fillMaxHeight(),
            contentAlignment = Alignment.Center
        ) {
            IconButton(onClick = navigationAction::onClick) {
                Icon(
                    painter = icon,
                    contentDescription = null,
                    modifier = Modifier.size(30.dp)
                )
            }
        }
    }
}

@Preview
@Composable
private fun AppBarPreview() {
    FoundationTheme {
        AppBar(
            config = Components.AppBar(
                visible = true,
                background = Constants.COLOR_NEUTRAL,
                title = Components.AppBar.Title(
                    visible = true,
                    alignment = Constants.ALIGNMENT_CENTER
                )
            ),
            title = stringResource(id = R.string.app_name),
            navigationAction = NavigationAction.Menu(isEnabled = true) { }
        )
    }
}
package app.mynta.template.android.core.components

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
import androidx.compose.material3.TopAppBarColors
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
import app.mynta.template.android.R
import app.mynta.template.android.core.Constants
import app.mynta.template.android.domain.model.app_config.AppBarConfig
import app.mynta.template.android.ui.theme.DynamicTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    appBarConfig: AppBarConfig,
    title: String,
    showBackButton: Boolean,
    onNavigationActionClick: () -> Unit
) {
    val backgroundModifier  = when (appBarConfig.background) {
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

    val navigationIcon = when (showBackButton) {
        true -> painterResource(id = R.drawable.icon_arrow_left_filled)
        else -> painterResource(id = R.drawable.icon_menu_filled)
    }

    if (appBarConfig.title.position == Constants.POSITION_CENTER) {
        CenterAlignedTopAppBar(
            modifier = Modifier
                .height(50.dp)
                .then(backgroundModifier),
            colors = appBarColors(appBarConfig = appBarConfig),
            title = {
                AppBarTitle(
                    appBarConfig = appBarConfig,
                    title = title
                )
            },
            navigationIcon = {
                AppBarActionIcon(
                    icon = navigationIcon,
                    onClick = onNavigationActionClick
                )
            }
        )
    } else {
        TopAppBar(
            modifier = Modifier
                .height(50.dp)
                .then(backgroundModifier),
            colors = appBarColors(appBarConfig = appBarConfig),
            title = {
                AppBarTitle(
                    appBarConfig = appBarConfig,
                    title = title
                )
            },
            navigationIcon = {
                AppBarActionIcon(
                    icon = navigationIcon,
                    onClick = onNavigationActionClick
                )
            }
        )
    }
}

@Composable
fun AppBarTitle(appBarConfig: AppBarConfig, title: String) {
    if (appBarConfig.title.display) {
        Box(
            modifier = Modifier.fillMaxHeight(),
            contentAlignment = Alignment.Center,
            content = {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium
                )
            }
        )
    }
}

@Composable
fun AppBarActionIcon(icon: Painter, onClick: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxHeight(),
        contentAlignment = Alignment.Center
    ) {
        IconButton(onClick = onClick) {
            Icon(
                modifier = Modifier.size(size = 30.dp),
                painter = icon,
                contentDescription = null
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun appBarColors(appBarConfig: AppBarConfig): TopAppBarColors {
    val contentColor = when (appBarConfig.background) {
        Constants.BACKGROUND_NEUTRAL -> MaterialTheme.colorScheme.onSurface
        else -> MaterialTheme.colorScheme.surface
    }
    return TopAppBarDefaults.smallTopAppBarColors(
        containerColor = Color.Transparent,
        titleContentColor = contentColor,
        navigationIconContentColor = contentColor,
        actionIconContentColor = contentColor
    )
}

@Composable
@Preview
fun AppBarPreview() {
    DynamicTheme {
        AppBar(
            appBarConfig = AppBarConfig(
                display = true,
                background = Constants.BACKGROUND_NEUTRAL,
                title = AppBarConfig.Title(
                    display = true,
                    position = Constants.POSITION_CENTER
                )
            ),
            title = stringResource(id = R.string.app_name),
            showBackButton = false,
            onNavigationActionClick = { }
        )
    }
}
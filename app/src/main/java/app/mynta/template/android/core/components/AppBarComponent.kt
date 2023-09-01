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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.mynta.template.android.R
import app.mynta.template.android.domain.model.app_config.AppBarConfig
import app.mynta.template.android.ui.theme.DynamicTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(appBarConfig: AppBarConfig, title: String, onNavigationClick: () -> Unit) {
    if (!appBarConfig.display) {
        return
    }

    val backgroundModifier  = when (appBarConfig.background) {
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

    if (appBarConfig.title.position == "center") {
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
                AppBarActionIcon(onClick = onNavigationClick)
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
                AppBarActionIcon(onClick = onNavigationClick)
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
fun AppBarActionIcon(onClick: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxHeight(),
        contentAlignment = Alignment.Center
    ) {
        IconButton(onClick = onClick) {
            Icon(
                modifier = Modifier.size(size = 30.dp),
                painter = painterResource(id = R.drawable.icon_menu_filled),
                contentDescription = stringResource(id = R.string.toggle_menu))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun appBarColors(appBarConfig: AppBarConfig): TopAppBarColors {
    val contentColor = when (appBarConfig.background) {
        "neutral" -> MaterialTheme.colorScheme.onSurface
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
                background = "neutral",
                title = AppBarConfig.Title(
                    display = true,
                    position = "center"
                )
            ),
            title = stringResource(id = R.string.app_name),
            onNavigationClick = { }
        )
    }
}
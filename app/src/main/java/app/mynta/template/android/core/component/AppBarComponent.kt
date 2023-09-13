package app.mynta.template.android.core.component

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

sealed class NavigationActionType {
    data class Menu(val isEnabled: Boolean) : NavigationActionType()
    data object Back : NavigationActionType()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    appBarConfig: AppBarConfig,
    title: String,
    navigationActionType: NavigationActionType,
    onNavigationActionClick: () -> Unit
) {
    val backgroundModifier = when (appBarConfig.background) {
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

    val appBarModifier = Modifier
        .height(50.dp)
        .then(backgroundModifier)

    val appBarTitle: @Composable () -> Unit = {
        AppBarTitle(
            appBarConfig = appBarConfig,
            title = title
        )
    }

    val appBarNavigationIcon: @Composable () -> Unit = {
        val navigationIcon = when (navigationActionType) {
            is NavigationActionType.Back -> R.drawable.icon_arrow_left_filled
            is NavigationActionType.Menu -> {
                if (navigationActionType.isEnabled) {
                    R.drawable.icon_menu_filled
                } else {
                    null
                }
            }
        }

        navigationIcon?.let {
            AppBarActionIcon(
                icon = painterResource(id = it),
                onClick = onNavigationActionClick
            )
        }
    }

    if (appBarConfig.title.position == Constants.POSITION_CENTER) {
        CenterAlignedTopAppBar(
            modifier = appBarModifier,
            colors = appBarColors(appBarConfig),
            title = appBarTitle,
            navigationIcon = appBarNavigationIcon
        )
    } else {
        TopAppBar(
            modifier = appBarModifier,
            colors = appBarColors(appBarConfig),
            title = appBarTitle,
            navigationIcon = appBarNavigationIcon
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
        contentAlignment = Alignment.Center,
        content = {
            DynamicClickableIcon(
                modifier = Modifier.size(30.dp),
                source = icon,
                onClick = onClick
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun appBarColors(appBarConfig: AppBarConfig): TopAppBarColors {
    val contentColor = if (appBarConfig.background == Constants.BACKGROUND_NEUTRAL) {
        MaterialTheme.colorScheme.onSurface
    } else {
        MaterialTheme.colorScheme.surface
    }

    return TopAppBarDefaults.topAppBarColors(
        containerColor = Color.Transparent,
        titleContentColor = contentColor,
        navigationIconContentColor = contentColor,
        actionIconContentColor = contentColor
    )
}

@Composable
@Preview
fun AppBarPreview() {
    DynamicTheme(darkTheme = false) {
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
            navigationActionType = NavigationActionType.Menu(isEnabled = true),
            onNavigationActionClick = { }
        )
    }
}
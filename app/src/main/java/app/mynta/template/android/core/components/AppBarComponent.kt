package app.mynta.template.android.core.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
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
import app.mynta.template.android.R
import app.mynta.template.android.domain.model.configuration.AppBar
import app.mynta.template.android.domain.model.configuration.Configuration
import app.mynta.template.android.ui.theme.DynamicTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(appBarConfig: AppBar, title: String, onActionClick: () -> Unit) {
    if(appBarConfig.display) {
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
        TopAppBar(
            modifier = Modifier.height(50.dp).then(backgroundModifier),
            title = {
                if (appBarConfig.displayTitle) {
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
            },
            colors = TopAppBarDefaults.smallTopAppBarColors(
                containerColor = Color.Transparent,
                titleContentColor = MaterialTheme.colorScheme.onSurface,
                navigationIconContentColor = MaterialTheme.colorScheme.onSurface,
                actionIconContentColor = MaterialTheme.colorScheme.onSurface
            ),
            navigationIcon = {
                Box(
                    modifier = Modifier.fillMaxHeight(),
                    contentAlignment = Alignment.Center
                ) {
                    IconButton(onClick = onActionClick) {
                        Icon(
                            modifier = Modifier
                                .width(30.dp)
                                .height(30.dp),
                            painter = painterResource(id = R.drawable.icon_menu_filled),
                            contentDescription = stringResource(id = R.string.toggle_menu))
                    }
                }
            }
        )
    }
}

@Composable
@Preview
fun AppBarPreview() {
    DynamicTheme {
        AppBar(
            appBarConfig = AppBar(
                display = true,
                background = "neutral",
                displayTitle = true
            ),
            title = stringResource(id = R.string.app_name),
            onActionClick = { }
        )
    }
}
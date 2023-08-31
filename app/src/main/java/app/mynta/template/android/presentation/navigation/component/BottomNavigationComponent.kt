package app.mynta.template.android.presentation.navigation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import app.mynta.template.android.domain.model.configuration.BottomNavigation
import app.mynta.template.android.domain.model.configuration.NavigationItem
import coil.compose.rememberAsyncImagePainter

@Composable
fun BottomNavigationBar(
    navigationConfig: BottomNavigation,
    navController: NavHostController,
    currentRoute: String,
    navigationItems: List<NavigationItem>,
) {
    if (!navigationConfig.display) {
        return
    }

    val containerColor = when (navigationConfig.background) {
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

    val contentColor = when (navigationConfig.background) {
        "neutral" -> MaterialTheme.colorScheme.onSurface
        else -> MaterialTheme.colorScheme.surface
    }

    val navBarHeight = if (navigationConfig.label == "hidden") {
        Modifier.height(55.dp)
    } else {
        Modifier
    }

    NavigationBar(
        modifier = Modifier
            .then(navBarHeight)
            .then(containerColor)
            .padding(horizontal = 15.dp),
        containerColor = Color.Transparent
    ) {
        navigationItems.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.id,
                alwaysShowLabel = navigationConfig.label != "selected",
                colors = NavigationBarItemDefaults.colors(
                    selectedTextColor = contentColor,
                    selectedIconColor = contentColor,
                    unselectedTextColor = contentColor,
                    unselectedIconColor = contentColor,
                    indicatorColor = MaterialTheme.colorScheme.primaryContainer
                ),
                label = {
                    if (navigationConfig.label != "hidden") {
                        Text(
                            modifier = Modifier,
                            text = item.label,
                            style = MaterialTheme.typography.labelMedium
                        )
                    }
                },
                icon = {
                    Icon(
                        modifier = Modifier.size(22.dp),
                        painter = rememberAsyncImagePainter(
                            model = item.icon,
                            contentScale = ContentScale.Crop,
                            filterQuality = FilterQuality.High
                        ),
                        contentDescription = null
                    )
                },
                onClick = {
                    navController.navigate(route = item.id)
                }
            )
        }
    }
}
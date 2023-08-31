package app.mynta.template.android.presentation.navigation.component

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import app.mynta.template.android.domain.model.configuration.NavigationItem
import coil.compose.rememberAsyncImagePainter

@Composable
fun BottomNavigation(
    navController: NavHostController,
    currentRoute: String,
    navigationItems: List<NavigationItem>,
) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface
    ) {
        navigationItems.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.id,
                label = {
                    Text(
                        modifier = Modifier,
                        text = item.label,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                },
                icon = {
                    Icon(
                        modifier = Modifier.width(25.dp).height(25.dp),
                        painter = rememberAsyncImagePainter(item.icon),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                },
                onClick = {
                    navController.navigate(route = item.id)
                }
            )
        }
    }
}
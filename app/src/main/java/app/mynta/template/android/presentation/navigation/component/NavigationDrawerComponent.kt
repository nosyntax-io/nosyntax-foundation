package app.mynta.template.android.presentation.navigation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import app.mynta.template.android.R
import app.mynta.template.android.domain.model.configuration.NavigationItem
import coil.compose.rememberAsyncImagePainter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationDrawer(
    coroutineScope: CoroutineScope,
    navController: NavHostController,
    currentRoute: String,
    navigationItems: List<NavigationItem>,
    drawerState: DrawerState,
    content: @Composable () -> Unit
) {
    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = drawerState.isOpen,
        drawerContent = {
            NavigationDrawerContent(
                coroutineScope = coroutineScope,
                navController = navController,
                currentRoute = currentRoute,
                navigationItems = navigationItems,
                drawerState = drawerState
            )
        },
        content = content
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationDrawerContent(
    coroutineScope: CoroutineScope,
    navController: NavHostController,
    currentRoute: String,
    navigationItems: List<NavigationItem>,
    drawerState: DrawerState
) {
    ModalDrawerSheet(
        modifier = Modifier.padding(end = 90.dp),
        drawerContainerColor = MaterialTheme.colorScheme.background,
        content = {
            NavigationDrawerHeader()
            LazyColumn(content = {
                items(navigationItems.size) { index ->
                    val item = navigationItems[index]
                    when (item.type) {
                        "divider" -> {
                            Divider(
                                modifier = Modifier.padding(vertical = 7.dp),
                                thickness = 1.dp,
                                color = MaterialTheme.colorScheme.surface
                            )
                        }
                        else -> {
                            NavigationItem(currentRoute = currentRoute, item = item, onClick = {
                                navController.navigate(route = item.id)
                                coroutineScope.launch {
                                    drawerState.close()
                                }
                            })
                        }
                    }
                }
            })
        }
    )
}

@Composable
fun NavigationDrawerHeader() {
    Box {
        Image(
            modifier = Modifier
                .fillMaxWidth().height(150.dp)
                .padding(all = 20.dp)
                .clip(shape = RoundedCornerShape(15.dp)),
            painter = painterResource(id = R.drawable.navigation_header),
            contentDescription = null,
            contentScale = ContentScale.Crop,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationItem(
    modifier: Modifier = Modifier,
    currentRoute: String,
    item: NavigationItem,
    onClick: () -> Unit
) {
    NavigationDrawerItem(
        shape = MaterialTheme.shapes.large,
        modifier = modifier
            .height(50.dp)
            .padding(horizontal = 20.dp),
        selected = currentRoute == item.id,
        onClick = onClick,
        colors = NavigationDrawerItemDefaults.colors(
            selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
            unselectedContainerColor = Color.Transparent
        ),
        label = {
            Text(
                modifier = Modifier,
                text = item.label,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        },
        icon = {
            Icon(
                modifier = Modifier
                    .width(25.dp)
                    .height(25.dp),
                painter = rememberAsyncImagePainter(item.icon),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface
            )
        }
    )
}
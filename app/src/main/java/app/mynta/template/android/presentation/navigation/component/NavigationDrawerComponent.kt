package app.mynta.template.android.presentation.navigation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import app.mynta.template.android.domain.model.app_config.SideMenuConfig
import app.mynta.template.android.domain.model.NavigationItem
import coil.compose.rememberAsyncImagePainter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationDrawer(
    sideMenuConfig: SideMenuConfig,
    coroutineScope: CoroutineScope,
    navController: NavHostController,
    currentRoute: String,
    navigationItems: List<NavigationItem>,
    drawerState: DrawerState,
    content: @Composable () -> Unit
) {
    if (!sideMenuConfig.display) {
        return
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = drawerState.isOpen,
        drawerContent = {
            NavigationDrawerContent(
                sideMenuConfig = sideMenuConfig,
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
    sideMenuConfig: SideMenuConfig,
    coroutineScope: CoroutineScope,
    navController: NavHostController,
    currentRoute: String,
    navigationItems: List<NavigationItem>,
    drawerState: DrawerState
) {
    val containerColor = when (sideMenuConfig.background) {
        "neutral" -> Modifier.background(color = MaterialTheme.colorScheme.surface)
        "solid" -> Modifier.background(color = MaterialTheme.colorScheme.primary)
        "gradient" -> Modifier.background(
            Brush.verticalGradient(
                colors = listOf(
                    MaterialTheme.colorScheme.primary,
                    MaterialTheme.colorScheme.secondary
                )
            )
        )
        else -> Modifier
    }

    ModalDrawerSheet(
        modifier = Modifier
            .padding(end = 90.dp)
            .clip(shape = RoundedCornerShape(topEnd = 15.dp, bottomEnd = 15.dp))
            .then(other = containerColor),
        drawerContainerColor = Color.Transparent,
        content = {
            NavigationDrawerHeader(sideMenuConfig = sideMenuConfig)
            Spacer(modifier = Modifier.height(20.dp))
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
                            NavigationItem(
                                sideMenuConfig = sideMenuConfig,
                                currentRoute = currentRoute,
                                item = item,
                                onClick = {
                                    navController.navigate(route = item.id)
                                    coroutineScope.launch {
                                        drawerState.close()
                                    }
                                }
                            )
                        }
                    }
                }
            })
        }
    )
}

@Composable
fun NavigationDrawerHeader(sideMenuConfig: SideMenuConfig) {
    val header = sideMenuConfig.header
    if (!header.display) {
        return
    }
    Box {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .padding(start = 20.dp, top = 20.dp, end = 20.dp)
                .clip(shape = RoundedCornerShape(15.dp)),
            painter = rememberAsyncImagePainter(
                model = header.image,
                filterQuality = FilterQuality.High
            ),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationItem(
    sideMenuConfig: SideMenuConfig,
    currentRoute: String,
    item: NavigationItem,
    onClick: () -> Unit
) {
    val contentColor = when (sideMenuConfig.background) {
        "neutral" -> MaterialTheme.colorScheme.onSurface
        else -> MaterialTheme.colorScheme.surface
    }

    NavigationDrawerItem(
        shape = MaterialTheme.shapes.large,
        modifier = Modifier
            .height(50.dp)
            .padding(horizontal = 20.dp),
        selected = currentRoute == item.id,
        onClick = onClick,
        colors = NavigationDrawerItemDefaults.colors(
            selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
            unselectedContainerColor = Color.Transparent,
            selectedTextColor = contentColor,
            selectedIconColor = contentColor,
            unselectedTextColor = contentColor,
            unselectedIconColor = contentColor,
        ),
        label = {
            Text(
                modifier = Modifier,
                text = item.label,
                style = MaterialTheme.typography.bodyMedium
            )
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
        }
    )
}
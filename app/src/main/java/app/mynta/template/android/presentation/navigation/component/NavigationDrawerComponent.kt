package app.mynta.template.android.presentation.navigation.component

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import app.mynta.template.android.core.utility.Utilities.getIconResourceId
import app.mynta.template.android.domain.model.NavigationItem
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
    ModalDrawerSheet {
        NavigationDrawerHeader()
        navigationItems.forEach { item ->
            NavigationItem(currentRoute = currentRoute, item = item, onClick = {
                navController.navigate(route = item.id)
                coroutineScope.launch { drawerState.close() }
            })
        }
    }
}

@Composable
fun NavigationDrawerHeader() {

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationItem(
    modifier: Modifier = Modifier,
    currentRoute: String,
    item: NavigationItem,
    onClick: () -> Unit
) {
    val iconResourceId = getIconResourceId(iconName = item.icon)
    NavigationDrawerItem(
        modifier = modifier,
        label = { Text(text = item.label) },
        selected = currentRoute == item.id,
        onClick = onClick,
        icon = {
            iconResourceId?.let {
                Icon(
                    modifier = Modifier.width(25.dp).height(25.dp),
                    painter = painterResource(id = it),
                    contentDescription = null
                )
            }
        }
    )
}
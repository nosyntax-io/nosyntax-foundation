package app.mynta.template.android.presentation.navigation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import app.mynta.template.android.core.utility.Utilities.setColorContrast
import app.mynta.template.android.domain.model.NavigationItem
import app.mynta.template.android.ui.theme.DynamicTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoreOptionsBottomSheet(
    navController: NavHostController,
    navigationItems: List<NavigationItem>,
    onDismiss: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState()

    ModalBottomSheet(
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.onBackground,
        sheetState = sheetState,
        onDismissRequest = onDismiss,
        dragHandle = {
            BottomSheetDragHandle()
        },
        content = {
            BottomSheetContent(
                navController = navController,
                navigationItems = navigationItems,
                onDismiss = {
                    coroutineScope.launch {
                        sheetState.hide()
                        onDismiss()
                    }
                }
            )
        }
    )
}

@Composable
private fun BottomSheetDragHandle() {
    Box(
        modifier = Modifier.padding(10.dp).width(40.dp).height(6.dp).background(
            color = setColorContrast(
                isDark = isSystemInDarkTheme(),
                color = MaterialTheme.colorScheme.surface
            ),
            shape = MaterialTheme.shapes.large
        )
    )
}

@Composable
private fun BottomSheetContent(
    navController: NavHostController,
    navigationItems: List<NavigationItem>,
    onDismiss: () -> Unit
) {
    LazyColumn {
        items(navigationItems.size) { index ->
            val item = navigationItems[index]
            BottomSheetItem(item = item, onClick = {
                navController.navigate(route = item.route.toString())
                onDismiss()
            })
        }
        item {
            Spacer(modifier = Modifier.height(35.dp))
        }
    }
}

@Composable
private fun BottomSheetItem(item: NavigationItem, onClick: () -> Unit) {
    Column(modifier = Modifier.padding(horizontal = 20.dp)) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onClick)
                .padding(10.dp),
            text = item.label ?: "",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Preview
@Composable
private fun MoreOptionsBottomSheetPreview() {
    DynamicTheme {
        /*MoreOptionsBottomSheet(
            navController = rememberNavController(),
            navigationItems = generateMockNavigationItems(itemCount = 5),
            onDismiss = { }
        )*/
    }
}
package app.mynta.template.android.presentation.navigation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import app.mynta.template.android.core.components.DynamicIcon
import app.mynta.template.android.domain.model.NavigationItem
import app.mynta.template.android.ui.theme.DynamicTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoreOptionsBottomSheet(
    coroutineScope: CoroutineScope,
    navController: NavHostController,
    navigationItems: List<NavigationItem>,
    onDismiss: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState()

    ModalBottomSheet(
        containerColor = MaterialTheme.colorScheme.background,
        sheetState = sheetState,
        onDismissRequest = onDismiss,
        dragHandle = {
            Box(modifier = Modifier.padding(15.dp).width(40.dp).height(6.dp).background(
                color = MaterialTheme.colorScheme.surfaceVariant,
                shape = MaterialTheme.shapes.large
            ))
        },
        content = {
            LazyColumn(modifier = Modifier.padding(bottom = 30.dp)) {
                items(navigationItems.size) { index ->
                    val item = navigationItems[index]
                    BottomSheetItem(item = item, onClick = {
                        navController.navigate(route = item.id)
                        coroutineScope.launch {
                            sheetState.hide()
                            onDismiss()
                        }
                    })
                }
            }
        }
    )
}

@Composable
private fun BottomSheetItem(item: NavigationItem, onClick: () -> Unit) {
    Column(modifier = Modifier
        .padding(horizontal = 25.dp, vertical = 10.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onClick),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colorScheme.surface,
                        shape = MaterialTheme.shapes.large
                    )
                    .padding(10.dp)
            ) {
                DynamicIcon(
                    modifier = Modifier.size(22.dp),
                    source = item.icon
                )
            }
            Spacer(modifier = Modifier.width(15.dp))
            Text(
                modifier = Modifier,
                text = item.label,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}

@Preview
@Composable
private fun MoreOptionsBottomSheetPreview() {
    DynamicTheme {
        val placeholderIcon = "https://img.icons8.com/?size=512&id=99291&format=png"
        MoreOptionsBottomSheet(
            coroutineScope = rememberCoroutineScope(),
            navController = rememberNavController(),
            navigationItems = listOf(
                NavigationItem("item1", "type", "Item 1", placeholderIcon),
                NavigationItem("item2", "type", "Item 2", placeholderIcon),
                NavigationItem("item3", "type", "Item 3", placeholderIcon),
                NavigationItem("item4", "type", "Item 4", placeholderIcon)
            ),
            onDismiss = { }
        )
    }
}
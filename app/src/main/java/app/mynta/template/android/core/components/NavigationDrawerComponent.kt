package app.mynta.template.android.core.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import app.mynta.template.android.R
import app.mynta.template.android.domain.model.MenuItem

@Composable
fun NavigationHeader() {
    Box(
        modifier = Modifier
            .width(100.dp)
            .height(140.dp)
            .background(color = Color.Blue),
        contentAlignment = Alignment.Center
    ) {

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationItem(
    modifier: Modifier = Modifier,
    currentRoute: String,
    item: MenuItem,
    onClick: () -> Unit
) {
    NavigationDrawerItem(
        modifier = modifier,
        label = { Text(text = item.label) },
        selected = currentRoute == item.route,
        onClick = onClick,
        icon = { Icon(imageVector = item.icon, contentDescription = null) }
    )
}
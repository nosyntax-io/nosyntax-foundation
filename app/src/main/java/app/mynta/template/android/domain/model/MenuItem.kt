package app.mynta.template.android.domain.model

import androidx.compose.ui.graphics.vector.ImageVector

data class MenuItem(
    val id: String,
    val label: String,
    val route: String,
    val icon: ImageVector,
)
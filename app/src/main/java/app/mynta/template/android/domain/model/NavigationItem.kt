package app.mynta.template.android.domain.model

import app.mynta.template.android.presentation.navigation.graph.Roles

data class NavigationItem(
    val id: String,
    val role: String,
    val label: String = "",
    val icon: Any = "",
    val deeplink: String = "",
    val type: String = ""
)

fun generateMockNavigationItems(itemCount: Int = 5): List<NavigationItem> {
    val placeholderIcon = "https://img.icons8.com/?size=512&id=99291&format=png"
    val items = mutableListOf<NavigationItem>()

    repeat(itemCount) { index ->
        val id = "item${index + 1}"
        val role = if (index % 2 == 0) Roles.ROLE_WEB else Roles.ROLE_DIVIDER
        val label = "Item ${index + 1}"
        val type = "regular"
        items.add(NavigationItem(id, role, label, placeholderIcon, type))
    }

    return items
}
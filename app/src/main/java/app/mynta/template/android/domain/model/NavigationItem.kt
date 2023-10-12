package app.mynta.template.android.domain.model

data class NavigationItem(
    val route: String,
    val label: String? = null,
    val icon: Any? = null,
    val deeplink: String
)
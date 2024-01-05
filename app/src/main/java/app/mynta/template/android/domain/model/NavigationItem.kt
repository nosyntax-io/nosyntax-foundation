package app.mynta.template.android.domain.model

data class NavigationItem(
    val route: String,
    val label: String?,
    val icon: String?,
    val deeplink: String
)
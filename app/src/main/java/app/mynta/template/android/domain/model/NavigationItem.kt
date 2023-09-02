package app.mynta.template.android.domain.model

data class NavigationItem(
    val id: String,
    val role: String,
    val label: String = "",
    val icon: String = ""
)
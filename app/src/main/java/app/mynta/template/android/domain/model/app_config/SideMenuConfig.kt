package app.mynta.template.android.domain.model.app_config

data class SideMenuConfig(
    val display: Boolean,
    val background: String,
    val header: Header,
    val items: List<Item>) {

    data class Header(
        val display: Boolean,
        val image: String
    )

    data class Item(
        val route: Int,
        val role: String,
        val label: String? = null,
        val icon: Any? = null,
        val deeplink: String
    )
}
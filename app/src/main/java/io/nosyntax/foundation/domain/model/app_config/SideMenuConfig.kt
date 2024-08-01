package io.nosyntax.foundation.domain.model.app_config

data class SideMenuConfig(
    val visible: Boolean,
    val background: String,
    val header: Header,
    val items: List<Item>
) {
    data class Header(
        val visible: Boolean,
        val image: String
    )

    data class Item(
        val route: String,
        val label: String?,
        val icon: String?,
        val url: String?
    )
}
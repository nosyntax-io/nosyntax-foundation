package app.mynta.template.android.domain.model.app_config

import app.mynta.template.android.domain.model.NavigationItem

data class SideMenuConfig(
    val display: Boolean,
    val background: String,
    val header: Header,
    val items: List<NavigationItem>) {

    data class Header(
        val display: Boolean,
        val image: String
    )
}
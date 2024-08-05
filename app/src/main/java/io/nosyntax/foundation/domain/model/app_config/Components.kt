package io.nosyntax.foundation.domain.model.app_config

import io.nosyntax.foundation.domain.model.NavigationItem

data class Components(
    val appBar: AppBar,
    val sideMenu: SideMenu,
    val navigationBar: NavigationBar,
    val loadingIndicator: LoadingIndicator
) {
    data class AppBar(
        val visible: Boolean,
        val background: String,
        val title: Title
    ) {
        data class Title(
            val visible: Boolean,
            val alignment: String
        )
    }

    data class SideMenu(
        val visible: Boolean,
        val background: String,
        val header: Header,
        val items: List<NavigationItem>
    ) {
        data class Header(
            val visible: Boolean,
            val image: String
        )
    }

    data class NavigationBar(
        val visible: Boolean,
        val background: String,
        val label: String,
        val items: List<NavigationItem>
    )

    data class LoadingIndicator(
        val visible: Boolean,
        val animation: String,
        val background: String,
        val color: String
    )
}
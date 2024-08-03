package io.nosyntax.foundation.domain.model.app_config

data class Components(
    val appBar: AppBar,
    val sideMenu: SideMenu,
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
        val items: List<Item>
    ) {
        data class Header(
            val visible: Boolean,
            val image: String
        )

        data class Item(
            val route: String?,
            val type: String,
            val label: String?,
            val icon: String?,
            val action: String? = null
        )
    }

    data class LoadingIndicator(
        val visible: Boolean,
        val animation: String,
        val background: String,
        val color: String
    )
}
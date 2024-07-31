package io.nosyntax.foundation.domain.model.app_config

data class SideMenuConfig(
    val visible: Boolean,
    val background: String,
    val header: Header
) {

    data class Header(
        val visible: Boolean,
        val image: String
    )
}
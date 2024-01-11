package io.nosyntax.foundation.domain.model.app_config

data class SideMenuConfig(
    val display: Boolean,
    val background: String,
    val header: Header
) {

    data class Header(
        val display: Boolean,
        val image: String
    )
}
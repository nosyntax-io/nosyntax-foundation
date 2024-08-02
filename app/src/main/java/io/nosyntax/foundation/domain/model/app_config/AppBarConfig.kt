package io.nosyntax.foundation.domain.model.app_config

data class AppBarConfig(
    val visible: Boolean,
    val background: String,
    val title: Title
) {

    data class Title(
        val visible: Boolean,
        val alignment: String
    )
}
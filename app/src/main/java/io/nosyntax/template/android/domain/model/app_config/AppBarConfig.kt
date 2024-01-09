package io.nosyntax.template.android.domain.model.app_config

data class AppBarConfig(
    val display: Boolean,
    val background: String,
    val title: Title) {

    data class Title(
        val display: Boolean,
        val position: String
    )
}
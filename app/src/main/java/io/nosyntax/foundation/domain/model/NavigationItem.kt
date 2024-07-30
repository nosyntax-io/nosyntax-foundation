package io.nosyntax.foundation.domain.model

data class NavigationItem(
    val id: String,
    val type: String,
    val label: String?,
    val icon: String?,
    val url: String?
)
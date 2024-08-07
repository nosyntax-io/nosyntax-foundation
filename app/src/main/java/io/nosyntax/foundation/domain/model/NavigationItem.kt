package io.nosyntax.foundation.domain.model

data class NavigationItem(
    val type: String,
    val route: String?,
    val label: String?,
    val icon: String?,
    val action: String? = null
)
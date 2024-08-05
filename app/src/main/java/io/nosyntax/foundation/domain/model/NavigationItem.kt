package io.nosyntax.foundation.domain.model

data class NavigationItem(
    val route: String?,
    val type: String,
    val label: String?,
    val icon: String?,
    val action: String? = null
)
package io.nosyntax.foundation.domain.model

import java.io.Serializable

data class NavigationItem(
    val type: String,
    val route: String?,
    val label: String?,
    val icon: String?,
    val action: String? = null
) : Serializable
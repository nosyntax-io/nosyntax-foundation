package io.nosyntax.foundation.domain.model.app_config

import io.nosyntax.foundation.domain.model.NavigationItem

data class NavigationConfig(
    val default: String,
    val items: List<NavigationItem>
)
package io.nosyntax.template.android.domain.model.app_config

import io.nosyntax.template.android.domain.model.NavigationItem

data class NavigationConfig(
    val default: String,
    val items: List<NavigationItem>
)
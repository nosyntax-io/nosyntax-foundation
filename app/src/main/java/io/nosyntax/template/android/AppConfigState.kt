package io.nosyntax.template.android

import io.nosyntax.template.android.domain.model.app_config.AppConfig

data class AppConfigState(
    val isLoading: Boolean = false,
    val response: AppConfig? = null,
    val error: String? = null
)
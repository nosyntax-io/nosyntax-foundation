package io.nosyntax.foundation

import io.nosyntax.foundation.domain.model.app_config.AppConfig

data class AppConfigState(
    val isLoading: Boolean = false,
    val response: AppConfig? = null,
    val error: String? = null
)
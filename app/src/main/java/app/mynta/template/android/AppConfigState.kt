package app.mynta.template.android

import app.mynta.template.android.domain.model.app_config.AppConfig

data class AppConfigState(
    val isLoading: Boolean = false,
    val response: AppConfig? = null,
    val error: String? = null
)
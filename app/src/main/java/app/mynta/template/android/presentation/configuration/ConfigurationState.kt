package app.mynta.template.android.presentation.configuration

import app.mynta.template.android.domain.model.Configuration

data class ConfigurationState(
    val isLoading: Boolean = false,
    val response: Configuration? = null,
    val error: String? = null
)
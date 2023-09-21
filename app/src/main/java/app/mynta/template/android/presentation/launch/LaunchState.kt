package app.mynta.template.android.presentation.launch

import app.mynta.template.android.domain.model.launch.Launch

data class LaunchState(
    val isLoading: Boolean = false,
    val response: Launch? = null,
    val error: String? = null
)
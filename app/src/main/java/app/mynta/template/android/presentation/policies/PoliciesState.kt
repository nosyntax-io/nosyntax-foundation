package app.mynta.template.android.presentation.policies

import app.mynta.template.android.domain.model.policies.Policies

data class PoliciesState(
    val isLoading: Boolean = false,
    val response: Policies? = null,
    val error: String? = null
)

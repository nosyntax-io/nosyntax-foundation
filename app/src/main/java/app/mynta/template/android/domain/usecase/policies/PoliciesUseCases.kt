package app.mynta.template.android.domain.usecase.policies

import javax.inject.Inject

data class PoliciesUseCases @Inject constructor(
    val getPoliciesUseCase: GetPoliciesUseCase
)
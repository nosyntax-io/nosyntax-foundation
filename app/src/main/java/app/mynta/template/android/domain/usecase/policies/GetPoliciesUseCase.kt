package app.mynta.template.android.domain.usecase.policies

import app.mynta.template.android.core.utility.Resource
import app.mynta.template.android.domain.model.policies.Policies
import app.mynta.template.android.domain.repository.PoliciesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPoliciesUseCase @Inject constructor(private val repository: PoliciesRepository) {
    suspend operator fun invoke(): Flow<Resource<Policies>> = repository.getPolicies()
}
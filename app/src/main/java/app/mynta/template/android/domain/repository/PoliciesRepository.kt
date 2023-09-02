package app.mynta.template.android.domain.repository

import app.mynta.template.android.core.utility.Resource
import app.mynta.template.android.domain.model.policies.Policies
import kotlinx.coroutines.flow.Flow

interface PoliciesRepository {
    suspend fun getPolicies(): Flow<Resource<Policies>>
}
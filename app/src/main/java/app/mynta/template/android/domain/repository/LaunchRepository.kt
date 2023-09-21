package app.mynta.template.android.domain.repository

import app.mynta.template.android.core.utility.Resource
import app.mynta.template.android.domain.model.launch.Launch
import kotlinx.coroutines.flow.Flow

interface LaunchRepository {
    suspend fun launch(): Flow<Resource<Launch>>
}
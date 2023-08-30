package app.mynta.template.android.domain.repository

import app.mynta.template.android.core.utility.Resource
import app.mynta.template.android.domain.model.Configuration
import kotlinx.coroutines.flow.Flow

interface ConfigurationRepository {
    suspend fun getConfiguration(): Flow<Resource<Configuration>>
}
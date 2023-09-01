package app.mynta.template.android.domain.repository

import app.mynta.template.android.core.utility.Resource
import app.mynta.template.android.domain.model.app_config.AppConfig
import kotlinx.coroutines.flow.Flow

interface AppConfigRepository {
    suspend fun getAppConfig(): Flow<Resource<AppConfig>>
}
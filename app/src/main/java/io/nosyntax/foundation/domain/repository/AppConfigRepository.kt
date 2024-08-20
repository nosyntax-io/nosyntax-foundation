package io.nosyntax.foundation.domain.repository

import io.nosyntax.foundation.core.util.Resource
import io.nosyntax.foundation.domain.model.app_config.AppConfig
import kotlinx.coroutines.flow.Flow

interface AppConfigRepository {
    suspend fun getAppConfig(): Flow<Resource<AppConfig>>
}
package io.nosyntax.template.android.domain.repository

import io.nosyntax.template.android.core.utility.Resource
import io.nosyntax.template.android.domain.model.app_config.AppConfig
import kotlinx.coroutines.flow.Flow

interface AppConfigRepository {
    suspend fun getAppConfig(): Flow<Resource<AppConfig>>
}
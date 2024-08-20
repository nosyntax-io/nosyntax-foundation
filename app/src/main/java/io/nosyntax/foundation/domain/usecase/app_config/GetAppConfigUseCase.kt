package io.nosyntax.foundation.domain.usecase.app_config

import io.nosyntax.foundation.core.util.Resource
import io.nosyntax.foundation.domain.model.app_config.AppConfig
import io.nosyntax.foundation.domain.repository.AppConfigRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAppConfigUseCase @Inject constructor(private val repository: AppConfigRepository) {
    suspend operator fun invoke(): Flow<Resource<AppConfig>> = repository.getAppConfig()
}
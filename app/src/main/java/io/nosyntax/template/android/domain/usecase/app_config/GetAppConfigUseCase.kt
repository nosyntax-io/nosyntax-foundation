package io.nosyntax.template.android.domain.usecase.app_config

import io.nosyntax.template.android.core.utility.Resource
import io.nosyntax.template.android.domain.model.app_config.AppConfig
import io.nosyntax.template.android.domain.repository.AppConfigRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAppConfigUseCase @Inject constructor(private val repository: AppConfigRepository) {
    suspend operator fun invoke(): Flow<Resource<AppConfig>> = repository.getAppConfig()
}
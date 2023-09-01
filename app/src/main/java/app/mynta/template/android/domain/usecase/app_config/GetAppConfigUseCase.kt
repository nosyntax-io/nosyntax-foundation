package app.mynta.template.android.domain.usecase.app_config

import app.mynta.template.android.core.utility.Resource
import app.mynta.template.android.domain.model.app_config.AppConfig
import app.mynta.template.android.domain.repository.AppConfigRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAppConfigUseCase @Inject constructor(private val repository: AppConfigRepository) {
    suspend operator fun invoke(): Flow<Resource<AppConfig>> = repository.getAppConfig()
}
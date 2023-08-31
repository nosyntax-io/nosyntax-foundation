package app.mynta.template.android.domain.usecase.configuration

import app.mynta.template.android.core.utility.Resource
import app.mynta.template.android.domain.model.configuration.Configuration
import app.mynta.template.android.domain.repository.ConfigurationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetConfigurationUseCase @Inject constructor(private val repository: ConfigurationRepository) {
    suspend operator fun invoke(): Flow<Resource<Configuration>> =
        repository.getConfiguration()
}
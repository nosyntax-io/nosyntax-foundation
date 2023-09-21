package app.mynta.template.android.domain.usecase.launch

import app.mynta.template.android.core.utility.Resource
import app.mynta.template.android.domain.model.launch.Launch
import app.mynta.template.android.domain.repository.LaunchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LaunchUseCase @Inject constructor(private val repository: LaunchRepository) {
    suspend operator fun invoke(): Flow<Resource<Launch>> = repository.launch()
}
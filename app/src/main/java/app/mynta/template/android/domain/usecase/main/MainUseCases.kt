package app.mynta.template.android.domain.usecase.main

import app.mynta.template.android.domain.usecase.configuration.GetConfigurationUseCase
import javax.inject.Inject

data class MainUseCases @Inject constructor(
    val getConfigurationUseCase: GetConfigurationUseCase
)
package io.nosyntax.foundation.domain.usecase.main

import io.nosyntax.foundation.domain.usecase.app_config.GetAppConfigUseCase
import javax.inject.Inject

data class MainUseCases @Inject constructor(
    val getAppConfigUseCase: GetAppConfigUseCase
)
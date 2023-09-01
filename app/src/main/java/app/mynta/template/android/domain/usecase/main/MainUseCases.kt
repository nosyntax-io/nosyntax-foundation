package app.mynta.template.android.domain.usecase.main

import app.mynta.template.android.domain.usecase.app_config.GetAppConfigUseCase
import javax.inject.Inject

data class MainUseCases @Inject constructor(
    val getAppConfigUseCase: GetAppConfigUseCase
)
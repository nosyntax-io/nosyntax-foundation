package app.mynta.template.android.domain.usecase.main

import app.mynta.template.android.domain.usecase.launch.LaunchUseCase
import javax.inject.Inject

data class MainUseCases @Inject constructor(
    val launchUseCase: LaunchUseCase
)
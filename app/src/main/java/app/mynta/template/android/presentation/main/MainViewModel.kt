package app.mynta.template.android.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.mynta.template.android.AppConfigState
import app.mynta.template.android.core.utility.Resource
import app.mynta.template.android.domain.model.app_config.AppConfig
import app.mynta.template.android.domain.usecase.main.MainUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val mainUseCases: MainUseCases): ViewModel() {
    private val _isInitialized = MutableStateFlow(false)
    val isInitialized = _isInitialized.asStateFlow()

    private val _appConfigUI = MutableStateFlow<AppConfig?>(null)
    val appConfigUI: StateFlow<AppConfig?> = _appConfigUI

    private val _appConfig = MutableStateFlow(AppConfigState())
    val appConfig: StateFlow<AppConfigState> = _appConfig

    init {
        requestAppConfig()
    }

    fun requestAppConfig() {
        viewModelScope.launch {
            mainUseCases.getAppConfigUseCase.invoke().collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        _appConfig.emit(AppConfigState(
                            isLoading = result.isLoading))
                    }
                    is Resource.Success -> {
                        _appConfig.emit(AppConfigState(
                            response = result.data))
                        _appConfigUI.value = result.data
                        _isInitialized.emit(true)
                    }
                    is Resource.Error -> {
                        _appConfig.emit(AppConfigState(
                            error = result.message))
                        _isInitialized.emit(true)
                    }
                }
            }
        }
    }
}
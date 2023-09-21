package app.mynta.template.android.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.mynta.template.android.core.utility.Resource
import app.mynta.template.android.domain.model.app_config.AppConfig
import app.mynta.template.android.domain.usecase.main.MainUseCases
import app.mynta.template.android.presentation.launch.LaunchState
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

    private val _appConfig = MutableStateFlow<AppConfig?>(null)
    val appConfig: StateFlow<AppConfig?> = _appConfig

    private val _launch = MutableStateFlow(LaunchState())
    val launch: StateFlow<LaunchState> = _launch

    init {
        requestLaunch()
    }

    fun requestLaunch() {
        viewModelScope.launch {
            mainUseCases.launchUseCase.invoke().collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        _launch.emit(LaunchState(
                            isLoading = result.isLoading))
                    }
                    is Resource.Success -> {
                        _launch.emit(
                            LaunchState(response = result.data)
                        )
                        result.data?.let { data ->
                            _appConfig.value = data.appConfig
                        }
                        _isInitialized.emit(true)
                    }
                    is Resource.Error -> {
                        _launch.emit(LaunchState(
                            error = result.message))
                        _isInitialized.emit(true)
                    }
                }
            }
        }
    }
}
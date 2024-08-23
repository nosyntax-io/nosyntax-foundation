package io.nosyntax.foundation.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.nosyntax.foundation.AppConfigState
import io.nosyntax.foundation.core.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import io.nosyntax.foundation.domain.usecase.app_config.GetAppConfigUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getAppConfigUseCase: GetAppConfigUseCase
) : ViewModel() {
    private val _isInitialized = MutableStateFlow(false)
    val isInitialized: StateFlow<Boolean> = _isInitialized.asStateFlow()

    private val _appConfig = MutableStateFlow(AppConfigState())
    val appConfig: StateFlow<AppConfigState> = _appConfig.asStateFlow()

    init {
        getAppConfig()
    }

    fun getAppConfig() {
        viewModelScope.launch {
            getAppConfigUseCase().collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        _appConfig.emit(
                            AppConfigState(
                                isLoading = result.isLoading
                            )
                        )
                    }
                    is Resource.Success -> {
                        _appConfig.emit(
                            AppConfigState(
                                response = result.data
                            )
                        )
                        _isInitialized.emit(true)
                    }
                    is Resource.Error -> {
                        _appConfig.emit(
                            AppConfigState(
                                error = result.message
                            )
                        )
                        _isInitialized.emit(true)
                    }
                }
            }
        }
    }
}
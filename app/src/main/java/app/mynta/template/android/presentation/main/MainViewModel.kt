package app.mynta.template.android.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.mynta.template.android.core.utility.Resource
import app.mynta.template.android.domain.model.Configuration
import app.mynta.template.android.domain.usecase.main.MainUseCases
import app.mynta.template.android.presentation.configuration.ConfigurationState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val mainUseCases: MainUseCases): ViewModel() {
    private val _configurationUI = MutableStateFlow<Configuration?>(null)
    val configurationUI: StateFlow<Configuration?> = _configurationUI

    private val _configuration = MutableStateFlow(ConfigurationState())
    val configuration = _configuration.asStateFlow()

    fun requestConfiguration() {
        viewModelScope.launch {
            mainUseCases.getConfigurationUseCase.invoke().collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        _configuration.emit(ConfigurationState(
                            isLoading = result.isLoading))
                    }
                    is Resource.Success -> {
                        _configuration.emit(ConfigurationState(
                            response = result.data))
                        _configurationUI.value = result.data
                    }
                    is Resource.Error -> {
                        _configuration.emit(ConfigurationState(
                            error = result.message))
                    }
                }
            }
        }
    }
}
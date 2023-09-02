package app.mynta.template.android.presentation.policies

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import app.mynta.template.android.core.utility.Resource
import app.mynta.template.android.domain.usecase.policies.PoliciesUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PoliciesViewModel @Inject constructor(app: Application, private val policiesUseCases: PoliciesUseCases): AndroidViewModel(app) {
    private val _policiesState = MutableStateFlow(PoliciesState())
    val policiesState: StateFlow<PoliciesState> = _policiesState

    init {
        requestPolicies()
    }

    fun requestPolicies() {
        viewModelScope.launch {
            policiesUseCases.getPoliciesUseCase.invoke().collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        _policiesState.emit(PoliciesState(
                            isLoading = result.isLoading))
                    }
                    is Resource.Success -> {
                        _policiesState.emit(PoliciesState(
                            response = result.data))
                    }
                    is Resource.Error -> {
                        _policiesState.emit(PoliciesState(
                            error = result.message))
                    }
                }
            }
        }
    }
}
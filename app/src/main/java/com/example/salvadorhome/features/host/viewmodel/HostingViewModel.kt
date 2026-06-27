package com.example.salvadorhome.features.host.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.salvadorhome.data.repository.HostingRepository
import com.example.salvadorhome.features.shared.model.Hosting
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class HostingUiState(
    val hostings: List<Hosting> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

class HostingViewModel(
    private val repository: HostingRepository = HostingRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(HostingUiState())
    val uiState: StateFlow<HostingUiState> = _uiState

    fun loadHostings() {
        _uiState.value = _uiState.value.copy(isLoading = true)

        viewModelScope.launch {
            val result = repository.getHostings()

            if (result.isSuccess) {
                _uiState.value = HostingUiState(
                    hostings = result.getOrNull() ?: emptyList(),
                    isLoading = false,
                    error = null
                )
            } else {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = result.exceptionOrNull()?.message
                )
            }
        }
    }

    fun publishHosting(
        title: String,
        location: String,
        description: String,
        pricePerNight: Double,
        capacity: Int,
        category: String,
        onSuccess: () -> Unit
    ) {
        viewModelScope.launch {
            // si ya tenías esta función, conserva tu versión de publicar
        }
    }
}
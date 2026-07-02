package com.example.salvadorhome.features.host.viewmodel

import android.net.Uri
import android.util.Log
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
    private val repository: HostingRepository
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
        imageUris: List<Uri>,
        onSuccess: () -> Unit
    ) {

        viewModelScope.launch {

            Log.d("HostingViewModel", "Intentando publicar...")

            val result = repository.publishHosting(
                title = title,
                location = location,
                description = description,
                pricePerNight = pricePerNight,
                capacity = capacity,
                category = category,
                imageUris = imageUris
            )

            if (result.isSuccess) {

                Log.d("HostingViewModel", "Publicación creada")

                onSuccess()

            } else {

                Log.e(
                    "HostingViewModel",
                    "Error",
                    result.exceptionOrNull()
                )

                _uiState.value = _uiState.value.copy(
                    error = result.exceptionOrNull()?.message
                )
            }
        }
    }

    fun loadMyHostings() {
        _uiState.value = _uiState.value.copy(isLoading = true)

        viewModelScope.launch {
            val result = repository.getMyHostings()

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

    fun updateHosting(
        hostingId: String,
        title: String,
        location: String,
        description: String,
        pricePerNight: Double,
        capacity: Int,
        category: String,
        onSuccess: () -> Unit
    ) {
        viewModelScope.launch {
            val result = repository.updateHosting(
                hostingId = hostingId,
                title = title,
                location = location,
                description = description,
                pricePerNight = pricePerNight,
                capacity = capacity,
                category = category
            )

            if (result.isSuccess) {
                onSuccess()
            } else {
                _uiState.value = _uiState.value.copy(
                    error = result.exceptionOrNull()?.message
                )
            }
        }
    }

}
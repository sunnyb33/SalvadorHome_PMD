package com.example.salvadorhome.features.profile.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.salvadorhome.data.repository.ProfileRepository
import com.example.salvadorhome.data.repository.UserProfile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class ProfileUiState(
    val isLoading: Boolean = false,
    val profile: UserProfile? = null,
    val error: String? = null
)

class ProfileViewModel(
    private val repository: ProfileRepository = ProfileRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState

    fun loadProfile() {
        _uiState.value = _uiState.value.copy(isLoading = true)

        viewModelScope.launch {
            val result = repository.getCurrentUserProfile()

            if (result.isSuccess) {
                _uiState.value = ProfileUiState(
                    isLoading = false,
                    profile = result.getOrNull(),
                    error = null
                )
            } else {
                _uiState.value = ProfileUiState(
                    isLoading = false,
                    profile = null,
                    error = result.exceptionOrNull()?.message
                )
            }
        }
    }

    fun logout() {
        repository.logout()
    }
}
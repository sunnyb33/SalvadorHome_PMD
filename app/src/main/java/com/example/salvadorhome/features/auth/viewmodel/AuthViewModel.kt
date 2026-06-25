package com.example.salvadorhome.features.auth.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.salvadorhome.data.model.UserEntity
import com.example.salvadorhome.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class AuthUiState(
    val nombre: String = "",
    val apellido: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val selectedRole: String? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val currentUser: UserEntity? = null
)

class AuthViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState

    fun onNombreChange(value: String) {
        _uiState.value = _uiState.value.copy(nombre = value)
    }

    fun onApellidoChange(value: String) {
        _uiState.value = _uiState.value.copy(apellido = value)
    }

    fun onEmailChange(value: String) {
        _uiState.value = _uiState.value.copy(email = value)
    }

    fun onPasswordChange(value: String) {
        _uiState.value = _uiState.value.copy(password = value)
    }

    fun onConfirmPasswordChange(value: String) {
        _uiState.value = _uiState.value.copy(confirmPassword = value)
    }

    fun onRoleSelected(role: String) {
        _uiState.value = _uiState.value.copy(selectedRole = role)
    }

    fun register(onSuccess: () -> Unit) {
        val state = _uiState.value

        if (state.nombre.isBlank() || state.apellido.isBlank() || state.email.isBlank() || state.password.isBlank()) {
            _uiState.value = state.copy(error = "Completa todos los campos")
            return
        }

        if (state.password != state.confirmPassword) {
            _uiState.value = state.copy(error = "Las contraseñas no coinciden")
            return
        }

        viewModelScope.launch {
            val existingUser = userRepository.getUserByEmail(state.email)

            if (existingUser != null) {
                _uiState.value = state.copy(error = "Este correo ya está registrado")
                return@launch
            }

            val user = UserEntity(
                nombre = state.nombre,
                apellido = state.apellido,
                email = state.email,
                password = state.password,
                role = state.selectedRole ?: "ARRENDATARIO"
            )

            userRepository.registerUser(user)

            _uiState.value = state.copy(
                isLoading = false,
                error = null,
                currentUser = user
            )

            onSuccess()
        }
    }

    fun login(onSuccess: () -> Unit) {
        val state = _uiState.value

        viewModelScope.launch {
            val user = userRepository.login(
                email = state.email,
                password = state.password
            )

            if (user != null) {
                _uiState.value = state.copy(
                    currentUser = user,
                    error = null
                )
                onSuccess()
            } else {
                _uiState.value = state.copy(
                    error = "Correo o contraseña incorrectos"
                )
            }
        }
    }
    fun insertTestUser() {

        viewModelScope.launch {

            val existingUser =
                userRepository.getUserByEmail(
                    "admin@test.com"
                )

            if (existingUser == null) {

                userRepository.registerUser(
                    UserEntity(
                        nombre = "Josue",
                        apellido = "Admin",
                        email = "admin@test.com",
                        password = "123456",
                        role = "AMBOS"
                    )
                )
            }
        }
    }
}
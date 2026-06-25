package com.example.salvadorhome.features.auth.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.salvadorhome.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    data class Success(val rol: String) : AuthState()
    data class Error(val message: String) : AuthState()
}

class AuthViewModel(
    private val repository: AuthRepository
) : ViewModel() {

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    fun login(email: String, clave: String) {
        _authState.value = AuthState.Loading

        viewModelScope.launch {
            val result = repository.login(email, clave)

            if (result.isSuccess) {
                val rol = result.getOrNull() ?: "ARRENDATARIO"
                _authState.value = AuthState.Success(rol)
            } else {
                _authState.value = AuthState.Error(
                    result.exceptionOrNull()?.message ?: "Error desconocido"
                )
            }
        }
    }

    fun register(
        email: String,
        clave: String,
        nombre: String,
        apellido: String,
        rol: String
    ) {
        _authState.value = AuthState.Loading

        viewModelScope.launch {
            val result = repository.register(
                email = email,
                clave = clave,
                nombre = nombre,
                apellido = apellido,
                rol = rol
            )

            if (result.isSuccess) {
                _authState.value = AuthState.Success(rol)
            } else {
                _authState.value = AuthState.Error(
                    result.exceptionOrNull()?.message ?: "Error al registrar"
                )
            }
        }
    }

    fun resetState() {
        _authState.value = AuthState.Idle
    }
}
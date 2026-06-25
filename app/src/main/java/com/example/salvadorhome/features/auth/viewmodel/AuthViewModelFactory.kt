package com.example.salvadorhome.features.auth.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.salvadorhome.data.repository.AuthRepository

// Le decimos que reciba el repositorio (con uno por defecto para que no de error)
class AuthViewModelFactory(
    private val repository: AuthRepository = AuthRepository()
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AuthViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
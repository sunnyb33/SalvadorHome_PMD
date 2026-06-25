package com.example.salvadorhome.features.auth.viewmodel

import android.content.Context
import com.example.salvadorhome.data.repository.AuthRepository

object AuthViewModelProvider {

    fun provideFactory(
        context: Context
    ): AuthViewModelFactory {

        // Simplemente le pasamos el repositorio de Firebase
        val repository = AuthRepository()

        return AuthViewModelFactory(repository)
    }
}
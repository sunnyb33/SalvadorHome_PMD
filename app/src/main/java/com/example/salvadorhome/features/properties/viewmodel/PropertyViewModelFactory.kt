package com.example.salvadorhome.features.properties.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.salvadorhome.data.repository.PropertyRepository

class PropertyViewModelFactory(
    private val propertyRepository: PropertyRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PropertyViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PropertyViewModel(propertyRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
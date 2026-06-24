package com.example.salvadorhome.features.properties.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.salvadorhome.data.model.PropertyEntity
import com.example.salvadorhome.data.model.toProperty
import com.example.salvadorhome.data.repository.PropertyRepository
import com.example.salvadorhome.features.properties.model.Property
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.UUID

data class PropertyUiState(
    val properties: List<Property> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

class PropertyViewModel(
    private val propertyRepository: PropertyRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(PropertyUiState())
    val uiState: StateFlow<PropertyUiState> = _uiState

    fun loadProperties() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            val properties = propertyRepository
                .getProperties()
                .map { it.toProperty() }

            _uiState.value = _uiState.value.copy(
                properties = properties,
                isLoading = false,
                error = null
            )
        }
    }

    fun publishProperty(
        title: String,
        location: String,
        description: String,
        pricePerNight: Double,
        capacity: Int,
        category: String,
        imageRes: Int
    ) {
        viewModelScope.launch {
            val property = PropertyEntity(
                id = UUID.randomUUID().toString(),
                title = title,
                location = location,
                description = description,
                pricePerNight = pricePerNight,
                capacity = capacity,
                category = category,
                imageRes = imageRes,
                isFavorite = false
            )

            propertyRepository.addProperty(property)
            loadProperties()
        }
    }
    fun insertTestProperty() {
        viewModelScope.launch {

            propertyRepository.addProperty(
                PropertyEntity(
                    id = UUID.randomUUID().toString(),
                    title = "Prueba Room",
                    location = "San Salvador",
                    description = "Hospedaje de prueba",
                    pricePerNight = 25.0,
                    capacity = 2,
                    category = "HOTEL",
                    imageRes = android.R.drawable.ic_menu_gallery
                )
            )

            loadProperties()
        }
    }
}


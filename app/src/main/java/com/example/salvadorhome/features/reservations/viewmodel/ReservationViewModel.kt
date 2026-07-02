package com.example.salvadorhome.features.reservations.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.salvadorhome.data.repository.ReservationRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

data class ReservationUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val success: Boolean = false
)

class ReservationViewModel(
    private val repository: ReservationRepository = ReservationRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(ReservationUiState())
    val uiState: StateFlow<ReservationUiState> = _uiState

    fun calculateNights(
        checkInMillis: Long,
        checkOutMillis: Long
    ): Int {
        return TimeUnit.MILLISECONDS.toDays(
            checkOutMillis - checkInMillis
        ).toInt()
    }

    fun calculateTotal(
        checkInMillis: Long,
        checkOutMillis: Long,
        pricePerNight: Double,
        cleaningFee: Double,
        serviceFee: Double
    ): Double {
        val nights = calculateNights(checkInMillis, checkOutMillis)
        return if (nights > 0) {
            (nights * pricePerNight) + cleaningFee + serviceFee
        } else {
            0.0
        }
    }

    fun createReservation(
        hostingId: String,
        hostingTitle: String,
        hostingLocation: String,
        ownerId: String,
        checkInMillis: Long,
        checkOutMillis: Long,
        guests: Int,
        pricePerNight: Double,
        cleaningFee: Double,
        serviceFee: Double,
        onSuccess: () -> Unit
    ) {
        _uiState.value = ReservationUiState(isLoading = true)

        viewModelScope.launch {
            val result = repository.createReservation(
                hostingId = hostingId,
                hostingTitle = hostingTitle,
                hostingLocation = hostingLocation,
                ownerId = ownerId,
                checkInMillis = checkInMillis,
                checkOutMillis = checkOutMillis,
                guests = guests,
                pricePerNight = pricePerNight,
                cleaningFee = cleaningFee,
                serviceFee = serviceFee
            )

            if (result.isSuccess) {
                _uiState.value = ReservationUiState(success = true)
                onSuccess()
            } else {
                _uiState.value = ReservationUiState(
                    isLoading = false,
                    error = result.exceptionOrNull()?.message ?: "Error al crear reserva"
                )
            }
        }
    }

    fun resetState() {
        _uiState.value = ReservationUiState()
    }
}
package com.example.salvadorhome.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reservations")
data class ReservationEntity(

    @PrimaryKey
    val imageRes: Int,
    val name: String,
    val location: String,
    val pricePerNight: Double,
    val maxGuests: Int,
    val cleaningFee: Double = 0.0,
    val serviceFee: Double = 0.0
)
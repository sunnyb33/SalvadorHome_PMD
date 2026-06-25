package com.example.salvadorhome.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "reservations")
data class ReservationEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val userId: Int,
    val propertyId: String,
    val checkIn: String,
    val checkOut: String,
    val guestCount: Int,
    val total: Double
)
package com.example.salvadorhome.features.reservations.model

data class Reservation(
    val id: String = "",
    val hostingId: String = "",
    val hostingTitle: String = "",
    val hostingLocation: String = "",
    val ownerId: String = "",
    val guestId: String = "",
    val checkInMillis: Long = 0L,
    val checkOutMillis: Long = 0L,
    val guests: Int = 1,
    val nights: Int = 0,
    val pricePerNight: Double = 0.0,
    val cleaningFee: Double = 0.0,
    val serviceFee: Double = 0.0,
    val total: Double = 0.0,
    val status: String = "CONFIRMADA",
    val createdAt: Long = System.currentTimeMillis()
)
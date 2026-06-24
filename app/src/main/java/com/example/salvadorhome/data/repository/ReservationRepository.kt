package com.example.salvadorhome.data.repository

import com.example.salvadorhome.data.local.dao.ReservationDao
import com.example.salvadorhome.data.model.ReservationEntity

class ReservationRepository(
    private val reservationDao: ReservationDao
) {

    suspend fun addReservation(reservation: ReservationEntity) {
        reservationDao.insertReservation(reservation)
    }

    suspend fun getReservations(): List<ReservationEntity> {
        return reservationDao.getAllReservations()
    }
}
package com.example.salvadorhome.data.local.dao

import androidx.room.*
import com.example.salvadorhome.data.model.ReservationEntity

@Dao
interface ReservationDao {

    @Insert
    suspend fun insertReservation(reservation: ReservationEntity)

    @Query("SELECT * FROM reservations")
    suspend fun getAllReservations(): List<ReservationEntity>

    @Delete
    suspend fun deleteReservation(reservation: ReservationEntity)
}

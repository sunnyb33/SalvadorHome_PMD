package com.example.salvadorhome.data.repository

import com.example.salvadorhome.features.reservations.model.Reservation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import java.util.concurrent.TimeUnit

class ReservationRepository {

    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    suspend fun createReservation(
        hostingId: String,
        hostingTitle: String,
        hostingLocation: String,
        ownerId: String,
        checkInMillis: Long,
        checkOutMillis: Long,
        guests: Int,
        pricePerNight: Double,
        cleaningFee: Double,
        serviceFee: Double
    ): Result<Boolean> {
        return try {
            val guestId = auth.currentUser?.uid
                ?: return Result.failure(Exception("Usuario no autenticado"))

            val nights = TimeUnit.MILLISECONDS.toDays(
                checkOutMillis - checkInMillis
            ).toInt()

            if (nights <= 0) {
                return Result.failure(Exception("La fecha de salida debe ser posterior a la entrada"))
            }

            val isAvailable = isDateRangeAvailable(
                hostingId = hostingId,
                checkInMillis = checkInMillis,
                checkOutMillis = checkOutMillis
            )

            if (!isAvailable) {
                return Result.failure(Exception("Estas fechas ya están reservadas"))
            }

            val subtotal = nights * pricePerNight
            val total = subtotal + cleaningFee + serviceFee

            val reservation = Reservation(
                hostingId = hostingId,
                hostingTitle = hostingTitle,
                hostingLocation = hostingLocation,
                ownerId = ownerId,
                guestId = guestId,
                checkInMillis = checkInMillis,
                checkOutMillis = checkOutMillis,
                guests = guests,
                nights = nights,
                pricePerNight = pricePerNight,
                cleaningFee = cleaningFee,
                serviceFee = serviceFee,
                total = total,
                status = "CONFIRMADA",
                createdAt = System.currentTimeMillis()
            )

            firestore.collection("Reservations")
                .add(reservation)
                .await()

            Result.success(true)

        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private suspend fun isDateRangeAvailable(
        hostingId: String,
        checkInMillis: Long,
        checkOutMillis: Long
    ): Boolean {
        val snapshot = firestore.collection("Reservations")
            .whereEqualTo("hostingId", hostingId)
            .whereEqualTo("status", "CONFIRMADA")
            .get()
            .await()

        return snapshot.documents.none { document ->
            val existingCheckIn = document.getLong("checkInMillis") ?: return@none false
            val existingCheckOut = document.getLong("checkOutMillis") ?: return@none false

            checkInMillis < existingCheckOut && checkOutMillis > existingCheckIn
        }
    }

    suspend fun getMyReservations(): Result<List<Reservation>> {
        return try {
            val guestId = auth.currentUser?.uid
                ?: return Result.failure(Exception("Usuario no autenticado"))

            val snapshot = firestore.collection("Reservations")
                .whereEqualTo("guestId", guestId)
                .get()
                .await()

            val reservations = snapshot.documents.map { document ->
                Reservation(
                    id = document.id,
                    hostingId = document.getString("hostingId") ?: "",
                    hostingTitle = document.getString("hostingTitle") ?: "",
                    hostingLocation = document.getString("hostingLocation") ?: "",
                    ownerId = document.getString("ownerId") ?: "",
                    guestId = document.getString("guestId") ?: "",
                    checkInMillis = document.getLong("checkInMillis") ?: 0L,
                    checkOutMillis = document.getLong("checkOutMillis") ?: 0L,
                    guests = document.getLong("guests")?.toInt() ?: 1,
                    nights = document.getLong("nights")?.toInt() ?: 0,
                    pricePerNight = document.getDouble("pricePerNight") ?: 0.0,
                    cleaningFee = document.getDouble("cleaningFee") ?: 0.0,
                    serviceFee = document.getDouble("serviceFee") ?: 0.0,
                    total = document.getDouble("total") ?: 0.0,
                    status = document.getString("status") ?: "CONFIRMADA",
                    createdAt = document.getLong("createdAt") ?: 0L
                )
            }

            Result.success(reservations)

        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getReservationsForMyHostings(): Result<List<Reservation>> {
        return try {
            val ownerId = auth.currentUser?.uid
                ?: return Result.failure(Exception("Usuario no autenticado"))

            val snapshot = firestore.collection("Reservations")
                .whereEqualTo("ownerId", ownerId)
                .get()
                .await()

            val reservations = snapshot.documents.map { document ->
                Reservation(
                    id = document.id,
                    hostingId = document.getString("hostingId") ?: "",
                    hostingTitle = document.getString("hostingTitle") ?: "",
                    hostingLocation = document.getString("hostingLocation") ?: "",
                    ownerId = document.getString("ownerId") ?: "",
                    guestId = document.getString("guestId") ?: "",
                    checkInMillis = document.getLong("checkInMillis") ?: 0L,
                    checkOutMillis = document.getLong("checkOutMillis") ?: 0L,
                    guests = document.getLong("guests")?.toInt() ?: 1,
                    nights = document.getLong("nights")?.toInt() ?: 0,
                    pricePerNight = document.getDouble("pricePerNight") ?: 0.0,
                    cleaningFee = document.getDouble("cleaningFee") ?: 0.0,
                    serviceFee = document.getDouble("serviceFee") ?: 0.0,
                    total = document.getDouble("total") ?: 0.0,
                    status = document.getString("status") ?: "CONFIRMADA",
                    createdAt = document.getLong("createdAt") ?: 0L
                )
            }

            Result.success(reservations)

        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun cancelReservation(
        reservationId: String
    ): Result<Boolean> {
        return try {
            val currentUserId = auth.currentUser?.uid
                ?: return Result.failure(Exception("Usuario no autenticado"))

            val documentRef = firestore
                .collection("Reservations")
                .document(reservationId)

            val document = documentRef.get().await()

            val guestId = document.getString("guestId") ?: ""
            val ownerId = document.getString("ownerId") ?: ""

            if (currentUserId != guestId && currentUserId != ownerId) {
                return Result.failure(Exception("No tienes permiso para cancelar esta reserva"))
            }

            documentRef.update(
                mapOf(
                    "status" to "CANCELADA",
                    "cancelledAt" to System.currentTimeMillis()
                )
            ).await()

            Result.success(true)

        } catch (e: Exception) {
            Result.failure(e)
        }
    }


}
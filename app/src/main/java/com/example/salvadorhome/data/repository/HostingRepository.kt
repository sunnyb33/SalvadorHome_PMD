package com.example.salvadorhome.data.repository

import androidx.compose.ui.graphics.Color
import com.example.salvadorhome.features.shared.model.Hosting
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class HostingRepository {

    private val firestore = FirebaseFirestore.getInstance()

    suspend fun getHostings(): Result<List<Hosting>> {
        return try {
            val snapshot = firestore
                .collection("Hostings")
                .get()
                .await()

            val hostings = snapshot.documents.mapNotNull { document ->
                val title = document.getString("title") ?: return@mapNotNull null
                val location = document.getString("location") ?: ""
                val description = document.getString("description") ?: ""
                val pricePerNight = document.getDouble("pricePerNight") ?: 0.0

                Hosting(
                    title = title,
                    location = location,
                    description = description,
                    price = "$$pricePerNight / noche",
                    palette = listOf(
                        Color(0xFFC2A5E7),
                        Color(0xFF665089)
                    )
                )
            }

            Result.success(hostings)

        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
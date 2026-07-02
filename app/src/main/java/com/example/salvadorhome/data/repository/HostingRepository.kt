package com.example.salvadorhome.data.repository

import androidx.compose.ui.graphics.Color
import com.example.salvadorhome.features.shared.model.Hosting
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import android.net.Uri
import com.example.salvadorhome.data.remote.CloudinaryUploader
import android.content.Context

class HostingRepository(private val context: Context) {

    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()
    private val uploader = CloudinaryUploader(context)

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
                val ownerId = document.getString("ownerId") ?: ""
                val capacity = document.getLong("capacity")?.toInt() ?: 1

                @Suppress("UNCHECKED_CAST")
                val imageUrls = document.get("imageUrls") as? List<String> ?: emptyList()

                val category = document.getString("category") ?: ""

                Hosting(
                    id = document.id,
                    title = title,
                    location = location,
                    description = description,
                    price = "$$pricePerNight / noche",
                    ownerId = document.getString("ownerId") ?: "",
                    imageUrls = imageUrls,
                    category = category,
                    capacity = capacity,
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

    private suspend fun uploadImages(uris: List<Uri>): List<String> {
        return uris.map { uploader.uploadImage(it) }
    }
    suspend fun publishHosting(
        title: String,
        location: String,
        description: String,
        pricePerNight: Double,
        capacity: Int,
        category: String,
        imageUris: List<Uri> = emptyList()
    ): Result<Boolean> {
        return try {
            val userId = auth.currentUser?.uid
                ?: return Result.failure(Exception("Usuario no autenticado"))

            val imageUrls = uploadImages(imageUris)

            val hostingMap = hashMapOf(
                "title" to title,
                "location" to location,
                "description" to description,
                "pricePerNight" to pricePerNight,
                "capacity" to capacity,
                "category" to category,
                "ownerId" to userId,
                "imageUrls" to imageUrls,
                "createdAt" to System.currentTimeMillis()
            )


            firestore
                .collection("Hostings")
                .add(hostingMap)
                .await()

            Result.success(true)

        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getMyHostings(): Result<List<Hosting>> {
        return try {
            val userId = auth.currentUser?.uid
                ?: return Result.failure(Exception("Usuario no autenticado"))

            val snapshot = firestore
                .collection("Hostings")
                .whereEqualTo("ownerId", userId)
                .get()
                .await()

            val hostings = snapshot.documents.mapNotNull { document ->
                val title = document.getString("title") ?: return@mapNotNull null
                val location = document.getString("location") ?: ""
                val description = document.getString("description") ?: ""
                val pricePerNight = document.getDouble("pricePerNight") ?: 0.0
                val capacity = document.getLong("capacity")?.toInt() ?: 1
                @Suppress("UNCHECKED_CAST")
                val imageUrls = document.get("imageUrls") as? List<String> ?: emptyList()

                val category = document.getString("category") ?: ""

                Hosting(
                    id = document.id,
                    title = title,
                    location = location,
                    description = description,
                    price = "$$pricePerNight / noche",
                    ownerId = document.getString("ownerId") ?: "",
                    imageUrls = imageUrls,
                    category = category,
                    capacity = capacity,
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

    suspend fun updateHosting(
        hostingId: String,
        title: String,
        location: String,
        description: String,
        pricePerNight: Double,
        capacity: Int,
        category: String
    ): Result<Boolean> {
        return try {
            val currentUserId = auth.currentUser?.uid
                ?: return Result.failure(Exception("Usuario no autenticado"))

            val documentRef = firestore
                .collection("Hostings")
                .document(hostingId)

            val document = documentRef.get().await()
            val ownerId = document.getString("ownerId") ?: ""

            if (ownerId != currentUserId) {
                return Result.failure(Exception("No tienes permiso para editar esta publicación"))
            }

            documentRef.update(
                mapOf(
                    "title" to title,
                    "location" to location,
                    "description" to description,
                    "pricePerNight" to pricePerNight,
                    "capacity" to capacity,
                    "category" to category,
                    "updatedAt" to System.currentTimeMillis()
                )
            ).await()

            Result.success(true)

        } catch (e: Exception) {
            Result.failure(e)
        }
    }


}
package com.example.salvadorhome.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

data class UserProfile(
    val nombre: String = "",
    val apellido: String = "",
    val email: String = "",
    val rol: String = ""
)

class ProfileRepository {

    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()

    suspend fun getCurrentUserProfile(): Result<UserProfile> {
        return try {
            val currentUser = auth.currentUser
                ?: return Result.failure(Exception("No hay usuario autenticado"))

            val document = firestore
                .collection("Usuarios")
                .document(currentUser.uid)
                .get()
                .await()

            val profile = UserProfile(
                nombre = document.getString("nombre") ?: "",
                apellido = document.getString("apellido") ?: "",
                email = document.getString("email") ?: currentUser.email.orEmpty(),
                rol = document.getString("rol") ?: ""
            )

            Result.success(profile)

        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun logout() {
        auth.signOut()
    }
}
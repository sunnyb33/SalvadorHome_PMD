package com.example.salvadorhome.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class AuthRepository {

    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()

    suspend fun login(email: String, clave: String): Result<String> {
        return try {
            val authResult = auth.signInWithEmailAndPassword(email, clave).await()
            val userId = authResult.user?.uid
                ?: return Result.failure(Exception("No se pudo obtener el ID del usuario"))

            val document = firestore
                .collection("Usuarios")
                .document(userId)
                .get()
                .await()

            val rol = document.getString("rol") ?: "ARRENDATARIO"

            Result.success(rol)

        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun register(
        email: String,
        clave: String,
        nombre: String,
        apellido: String,
        rol: String
    ): Result<Boolean> {
        return try {
            val authResult = auth.createUserWithEmailAndPassword(email, clave).await()
            val userId = authResult.user?.uid
                ?: return Result.failure(Exception("Error al generar el ID de usuario"))

            val userMap = hashMapOf(
                "email" to email,
                "nombre" to nombre,
                "apellido" to apellido,
                "rol" to rol,
                "fechaCreacion" to System.currentTimeMillis()
            )

            firestore
                .collection("Usuarios")
                .document(userId)
                .set(userMap)
                .await()

            Result.success(true)

        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun isUserLoggedIn(): Boolean = auth.currentUser != null

    fun logout() = auth.signOut()
}
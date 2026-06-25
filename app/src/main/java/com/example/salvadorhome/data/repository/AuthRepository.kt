package com.example.salvadorhome.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class AuthRepository {
    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()

    suspend fun login(email: String, clave: String): Result<Boolean> {
        return try {
            auth.signInWithEmailAndPassword(email, clave).await()
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // NUEVO: Agregamos nombre y apellido a la función
    suspend fun register(email: String, clave: String, nombre: String, apellido: String, rol: String): Result<Boolean> {
        return try {
            val authResult = auth.createUserWithEmailAndPassword(email, clave).await()
            val userId = authResult.user?.uid

            if (userId != null) {
                // NUEVO: Guardamos el nombre y apellido en Firestore
                val userMap = hashMapOf(
                    "email" to email,
                    "nombre" to nombre,
                    "apellido" to apellido,
                    "rol" to rol,
                    "fechaCreacion" to System.currentTimeMillis()
                )
                firestore.collection("Usuarios").document(userId).set(userMap).await()
                Result.success(true)
            } else {
                Result.failure(Exception("Error al generar el ID de usuario"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun isUserLoggedIn(): Boolean = auth.currentUser != null
    fun logout() = auth.signOut()
}
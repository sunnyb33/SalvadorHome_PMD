package com.example.salvadorhome.data.remote

import android.content.Context
import android.net.Uri
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

class CloudinaryUploader(private val context: Context) {

    private val client = OkHttpClient()
    private val cloudName = "phvghzoq"
    private val uploadPreset = "salvadorhome_unsigned"

    suspend fun uploadImage(uri: Uri): String = withContext(Dispatchers.IO) {
        val bytes = context.contentResolver.openInputStream(uri)?.use { it.readBytes() }
            ?: throw Exception("No se pudo leer la imagen")

        val requestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart(
                "file", "image.jpg",
                bytes.toRequestBody("image/*".toMediaTypeOrNull())
            )
            .addFormDataPart("upload_preset", uploadPreset)
            .build()

        val request = Request.Builder()
            .url("https://api.cloudinary.com/v1_1/$cloudName/image/upload")
            .post(requestBody)
            .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) {
                throw Exception("Error al subir imagen: ${response.code}")
            }
            val json = JSONObject(response.body!!.string())
            json.getString("secure_url")   // 👈 esta es la URL que guardas en Firestore
        }
    }
}
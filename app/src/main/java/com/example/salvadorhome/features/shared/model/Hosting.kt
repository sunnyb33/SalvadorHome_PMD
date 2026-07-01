package com.example.salvadorhome.features.shared.model

import androidx.compose.ui.graphics.Color


data class Hosting(
    val title: String,
    val location: String,
    val description: String,
    val price: String,
    val status: String = "Publicado",
    val palette: List<Color>,
    val id: String = "",
    val ownerId: String = "",
    val imageUrls: List<String> = emptyList()
)

val SampleHostings = listOf(
    Hosting(
        title = "Casa frente al mar",
        location = "La Libertad",
        description = "Espacio tranquilo con vista al océano, cocina y estacionamiento.",
        price = "$85 / noche",
        palette = listOf(Color(0xFFD7B28C), Color(0xFF78543C))
    ),
    Hosting(
        title = "Cabaña entre montañas",
        location = "Apaneca",
        description = "Una cabaña acogedora rodeada de naturaleza.",
        price = "$62 / noche",
        palette = listOf(Color(0xFF8BB79C), Color(0xFF315C4B))
    ),
    Hosting(
        title = "Apartamento céntrico",
        location = "San Salvador",
        description = "Moderno, cómodo y cerca de restaurantes.",
        price = "$48 / noche",
        palette = listOf(Color(0xFF92A9C6), Color(0xFF354A68))
    )
)

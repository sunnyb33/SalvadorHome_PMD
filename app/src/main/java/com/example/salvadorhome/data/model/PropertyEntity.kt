package com.example.salvadorhome.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "properties")
data class PropertyEntity(

    @PrimaryKey
    val id: String,
    val title: String,
    val location: String,
    val description: String,
    val pricePerNight: Double,
    val capacity: Int,
    val category: String,
    val imageRes: Int,
    val isFavorite: Boolean = false
)
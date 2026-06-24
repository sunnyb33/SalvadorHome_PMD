package com.example.salvadorhome.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.salvadorhome.data.model.PropertyEntity

@Dao
interface PropertyDao {

    @Insert
    suspend fun insertProperty(property: PropertyEntity)

    @Query("SELECT * FROM properties")
    suspend fun getAllProperties(): List<PropertyEntity>

    @Delete
    suspend fun deleteProperty(property: PropertyEntity)
}
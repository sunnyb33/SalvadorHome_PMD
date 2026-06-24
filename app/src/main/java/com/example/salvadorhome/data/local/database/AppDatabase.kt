package com.example.salvadorhome.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.salvadorhome.data.local.dao.PropertyDao
import com.example.salvadorhome.data.local.dao.ReservationDao
import com.example.salvadorhome.data.local.dao.UserDao
import com.example.salvadorhome.data.model.PropertyEntity
import com.example.salvadorhome.data.model.ReservationEntity
import com.example.salvadorhome.data.model.UserEntity

@Database(
    entities = [
        UserEntity::class,
        PropertyEntity::class,
        ReservationEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    abstract fun propertyDao(): PropertyDao

    abstract fun reservationDao(): ReservationDao
}
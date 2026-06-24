package com.example.salvadorhome.data.local.database

import android.content.Context
import androidx.room.Room

object DatabaseProvider {

    @Volatile
    private var INSTANCE: AppDatabase? = null

    fun getDatabase(
        context: Context
    ): AppDatabase {

        return INSTANCE ?: synchronized(this) {

            val instance = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "salvador_home_database"
            ).build()

            INSTANCE = instance

            instance
        }
    }
}
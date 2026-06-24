package com.example.salvadorhome.features.properties.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.example.salvadorhome.data.local.database.DatabaseProvider
import com.example.salvadorhome.data.repository.PropertyRepository

object PropertyViewModelProvider {

    fun provideFactory(
        context: Context
    ): PropertyViewModelFactory {

        val database =
            DatabaseProvider.getDatabase(context)

        val repository =
            PropertyRepository(
                database.propertyDao()
            )

        return PropertyViewModelFactory(
            repository
        )
    }
}
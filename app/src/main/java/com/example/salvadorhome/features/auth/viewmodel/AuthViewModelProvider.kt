package com.example.salvadorhome.features.auth.viewmodel

import android.content.Context
import com.example.salvadorhome.data.local.database.DatabaseProvider
import com.example.salvadorhome.data.repository.UserRepository

object AuthViewModelProvider {

    fun provideFactory(
        context: Context
    ): AuthViewModelFactory {

        val database =
            DatabaseProvider.getDatabase(context)

        val repository =
            UserRepository(
                database.userDao()
            )

        return AuthViewModelFactory(
            repository
        )
    }
}
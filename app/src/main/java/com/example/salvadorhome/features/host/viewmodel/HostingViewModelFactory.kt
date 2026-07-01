package com.example.salvadorhome.features.host.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.salvadorhome.data.repository.HostingRepository

class HostingViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return HostingViewModel(HostingRepository(context.applicationContext)) as T
    }
}
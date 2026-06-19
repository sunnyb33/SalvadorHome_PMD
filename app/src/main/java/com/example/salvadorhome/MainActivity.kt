package com.example.salvadorhome

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.salvadorhome.core.theme.SalvadorHomeTheme
import com.example.salvadorhome.features.host.ui.HostApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SalvadorHomeTheme {
                HostApp()
            }
        }
    }
}

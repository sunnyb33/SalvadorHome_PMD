package com.example.salvadorhome.features.host.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bed
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.salvadorhome.core.theme.SalvadorHomeTheme
import com.example.salvadorhome.core.theme.SalvadorLavender
import com.example.salvadorhome.core.theme.SalvadorTextSecondary
import com.example.salvadorhome.features.host.ui.screens.HostDashboardScreen
import com.example.salvadorhome.features.host.ui.screens.HostingDetailScreen
import com.example.salvadorhome.features.host.ui.screens.PublishHostingScreen
import com.example.salvadorhome.features.shared.model.Hosting
import com.example.salvadorhome.features.shared.model.SampleHostings
import com.example.salvadorhome.features.shared.model.Conversation
import com.example.salvadorhome.features.shared.model.SampleConversations
import com.example.salvadorhome.features.shared.ui.components.SalvadorBottomBar
import com.example.salvadorhome.features.shared.ui.screens.ChatScreen
import com.example.salvadorhome.features.shared.ui.screens.ExploreScreen
import com.example.salvadorhome.features.shared.ui.screens.MessagesScreen

private enum class HostDestination { HOME, EXPLORE, MESSAGES, PUBLISH, BOOKINGS, PROFILE }

@Composable
fun HostApp() {
    var destination by remember { mutableStateOf(HostDestination.HOME) }
    var selectedHosting by remember { mutableStateOf<Hosting?>(null) }
    var selectedConversation by remember { mutableStateOf<Conversation?>(null) }
    val hostings = remember { mutableStateListOf<Hosting>().apply { addAll(SampleHostings) } }

    Scaffold(
        bottomBar = {
            SalvadorBottomBar(
                selectedIndex = destination.ordinal,
                onItemSelected = { index ->
                    selectedHosting = null
                    selectedConversation = null
                    destination = HostDestination.entries[index]
                }
            )
        }
    ) { padding ->
        val detail = selectedHosting
        if (detail != null) {
            HostingDetailScreen(
                hosting = detail,
                onBack = { selectedHosting = null },
                modifier = Modifier.padding(padding)
            )
        } else if (selectedConversation != null) {
            ChatScreen(
                conversation = selectedConversation!!,
                modifier = Modifier.padding(padding),
                onBack = { selectedConversation = null }
            )
        } else when (destination) {
            HostDestination.HOME -> HostDashboardScreen(
                hostings, Modifier.padding(padding),
                onHostingClick = { selectedHosting = it },
                onPublishClick = { destination = HostDestination.PUBLISH }
            )
            HostDestination.EXPLORE -> ExploreScreen(hostings, Modifier.padding(padding)) { selectedHosting = it }
            HostDestination.PUBLISH -> PublishHostingScreen(
                Modifier.padding(padding),
                onBack = { destination = HostDestination.HOME }
            ) { title, location, description ->
                hostings.add(0, Hosting(title, location, description, "Precio por definir", palette = listOf(Color(0xFFC2A5E7), Color(0xFF665089))))
                destination = HostDestination.HOME
            }
            HostDestination.MESSAGES -> MessagesScreen(
                conversations = SampleConversations,
                modifier = Modifier.padding(padding),
                onBack = { destination = HostDestination.HOME },
                onConversationClick = { selectedConversation = it }
            )
            HostDestination.BOOKINGS -> EmptySection("Reservas", "Administra las solicitudes y próximas estadías.", Icons.Default.Bed, Modifier.padding(padding))
            HostDestination.PROFILE -> EmptySection("Perfil", "Configura tu cuenta y la información de anfitrión.", Icons.Default.Person, Modifier.padding(padding))
        }
    }
}

@Composable
private fun EmptySection(title: String, subtitle: String, icon: ImageVector, modifier: Modifier) {
    Column(
        modifier = modifier.fillMaxSize().padding(28.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Surface(color = SalvadorLavender, shape = CircleShape) {
            Icon(icon, null, modifier = Modifier.padding(20.dp).size(42.dp))
        }
        Text(title, fontSize = 24.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 18.dp))
        Text(subtitle, color = SalvadorTextSecondary, textAlign = TextAlign.Center, modifier = Modifier.padding(top = 8.dp))
    }
}

@Preview(showBackground = true, widthDp = 390, heightDp = 844)
@Composable
private fun HostAppPreview() = SalvadorHomeTheme { HostApp() }

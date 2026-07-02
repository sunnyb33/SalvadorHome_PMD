package com.example.salvadorhome.features.host.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bed
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.salvadorhome.core.theme.SalvadorHomeTheme
import com.example.salvadorhome.core.theme.SalvadorLavender
import com.example.salvadorhome.core.theme.SalvadorTextSecondary
import com.example.salvadorhome.features.host.ui.screens.EditHostingScreen
import com.example.salvadorhome.features.host.ui.screens.HostDashboardScreen
import com.example.salvadorhome.features.host.ui.screens.HostingDetailScreen
import com.example.salvadorhome.features.host.ui.screens.PublishHostingScreen
import com.example.salvadorhome.features.host.viewmodel.HostingViewModel
import com.example.salvadorhome.features.profile.ui.ProfileScreen
import com.example.salvadorhome.features.shared.model.Hosting
import com.example.salvadorhome.features.shared.model.Conversation
import com.example.salvadorhome.features.shared.model.SampleConversations
import com.example.salvadorhome.features.shared.ui.components.SalvadorBottomBar
import com.example.salvadorhome.features.shared.ui.screens.ChatScreen
import com.example.salvadorhome.features.shared.ui.screens.ExploreScreen
import com.example.salvadorhome.features.shared.ui.screens.MessagesScreen
import androidx.compose.ui.platform.LocalContext
import com.example.salvadorhome.features.host.viewmodel.HostingViewModelFactory
import com.example.salvadorhome.features.reservations.ui.MyReservationsScreen

private enum class HostDestination { HOME, EXPLORE, MESSAGES, PUBLISH, BOOKINGS, PROFILE }

@Composable
fun HostApp(onLogout: () -> Unit = {}) {
    var destination by remember { mutableStateOf(HostDestination.HOME) }
    var selectedHosting by remember { mutableStateOf<Hosting?>(null) }
    var selectedConversation by remember { mutableStateOf<Conversation?>(null) }
    val context = LocalContext.current
    val hostingViewModel: HostingViewModel = viewModel(
        factory = HostingViewModelFactory(context)
    )
    val hostingState by hostingViewModel.uiState.collectAsState()
    var editingHosting by remember {
        mutableStateOf<Hosting?>(null)
    }
    LaunchedEffect(Unit) {
        hostingViewModel.loadMyHostings()
    }
    Scaffold(
        bottomBar = {
            if (editingHosting == null && selectedHosting == null && selectedConversation == null) {
                SalvadorBottomBar(
                    selectedIndex = destination.ordinal,
                    showExplore = false,
                    onItemSelected = { index ->
                        selectedHosting = null
                        selectedConversation = null
                        editingHosting = null
                        destination = HostDestination.entries[index]
                    }
                )
            }
        }
    ) { padding ->
        val detail = selectedHosting
        if (editingHosting != null) {

            EditHostingScreen(
                hosting = editingHosting!!,
                modifier = Modifier,
                onBack = {
                    editingHosting = null
                },
                onSave = { title, location, description, pricePerNight, capacity, category ->

                    hostingViewModel.updateHosting(
                        hostingId = editingHosting!!.id,
                        title = title,
                        location = location,
                        description = description,
                        pricePerNight = pricePerNight,
                        capacity = capacity,
                        category = category,
                        onSuccess = {
                            editingHosting = null
                            selectedHosting = null
                            hostingViewModel.loadMyHostings()
                        }
                    )
                }
            )

        } else if (detail != null) {
            HostingDetailScreen(
                hosting = detail,
                onBack = { selectedHosting = null },
                modifier = Modifier.padding(padding),
                onEditClick = { hosting ->
                    editingHosting = hosting
                },
                onDeleteClick = { hosting ->
                    hostingViewModel.deleteHosting(
                        hostingId = hosting.id,
                        onSuccess = {
                            selectedHosting = null
                            hostingViewModel.loadMyHostings()
                        }
                    )
                }
            )
        } else if (selectedConversation != null) {
            ChatScreen(
                conversation = selectedConversation!!,
                modifier = Modifier.padding(padding),
                onBack = { selectedConversation = null }
            )
        } else when (destination) {
            HostDestination.HOME -> HostDashboardScreen(
                hostings = hostingState.hostings,
                modifier = Modifier.padding(padding),
                onHostingClick = { selectedHosting = it },
                onPublishClick = { destination = HostDestination.PUBLISH }
            )

            HostDestination.EXPLORE -> ExploreScreen(
                hostings = hostingState.hostings,
                modifier = Modifier.padding(padding),
                onHostingClick = { hosting ->
                    selectedHosting = hosting
                }
            )

            HostDestination.PUBLISH -> PublishHostingScreen(
                Modifier.padding(padding),
                onBack = { destination = HostDestination.HOME }
            ) { title, location, description, pricePerNight, capacity, category, imageUris ->

                hostingViewModel.publishHosting(
                    title = title,
                    location = location,
                    description = description,
                    pricePerNight = pricePerNight,
                    capacity = capacity,
                    category = category,
                    imageUris = imageUris,
                    onSuccess = {
                        hostingViewModel.loadMyHostings()
                        destination = HostDestination.HOME
                    }
                )
            }

            HostDestination.MESSAGES -> MessagesScreen(
                conversations = SampleConversations,
                modifier = Modifier.padding(padding),
                onBack = { destination = HostDestination.HOME },
                onConversationClick = { selectedConversation = it }
            )

            HostDestination.BOOKINGS -> MyReservationsScreen(
                modifier = Modifier.padding(padding),
                isHostMode = true
            )

            HostDestination.PROFILE -> ProfileScreen(
                modifier = Modifier.padding(padding), onLogout = onLogout
            )
        }
    }
    LaunchedEffect(hostingState.error) {
        hostingState.error?.let {
            android.util.Log.e("PUBLISH_DEBUG", "Error real: $it")
        }
    }
}
@Composable
private fun EmptySection(title: String, subtitle: String, icon: ImageVector, modifier: Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(28.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Surface(color = SalvadorLavender, shape = CircleShape) {
            Icon(icon, null, modifier = Modifier
                .padding(20.dp)
                .size(42.dp))
        }
        Text(
            title,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 18.dp)
        )
        Text(
            subtitle,
            color = SalvadorTextSecondary,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

@Preview(showBackground = true, widthDp = 390, heightDp = 844)
@Composable
private fun HostAppPreview() = SalvadorHomeTheme { HostApp() }

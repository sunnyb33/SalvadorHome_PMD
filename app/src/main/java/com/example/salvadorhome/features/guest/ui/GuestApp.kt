package com.example.salvadorhome.features.guest.ui

import android.R
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.salvadorhome.features.home.ui.HomeScreen
import com.example.salvadorhome.features.profile.ui.ProfileScreen
import com.example.salvadorhome.features.shared.ui.components.SalvadorBottomBar
import androidx.compose.material3.Scaffold
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.salvadorhome.features.host.ui.screens.HostingDetailScreen
import com.example.salvadorhome.features.host.ui.screens.PublishHostingScreen
import com.example.salvadorhome.features.host.viewmodel.HostingViewModel
import com.example.salvadorhome.features.host.viewmodel.HostingViewModelFactory
import com.example.salvadorhome.features.profile.ui.HelpSupportScreen
import com.example.salvadorhome.features.profile.ui.NotificationsScreen
import com.example.salvadorhome.features.profile.ui.PrivacySecurityScreen
import com.example.salvadorhome.features.reservations.ui.ReservationProperty
import com.example.salvadorhome.features.reservations.ui.ReservationScreen
import com.example.salvadorhome.features.shared.model.Conversation
import com.example.salvadorhome.features.shared.model.Hosting
import com.example.salvadorhome.features.shared.model.SampleConversations
import com.example.salvadorhome.features.shared.model.SampleHostings
import com.example.salvadorhome.features.shared.ui.screens.ChatScreen
import com.example.salvadorhome.features.shared.ui.screens.ExploreScreen
import com.example.salvadorhome.features.shared.ui.screens.MessagesScreen

private enum class GuestDestination {
    HOME,
    EXPLORE,
    MESSAGES,
    PUBLISH,
    BOOKINGS,
    PROFILE
}

private enum class ProfileSubScreen {
    NONE,
    NOTIFICATIONS,
    SECURITY,
    HELP
}

@Composable
fun GuestApp(
    userRole: String = "",
    onHostClick: () -> Unit = {}
) {
    var destination by remember {
        mutableStateOf(GuestDestination.HOME)
    }
    val context = LocalContext.current
    val hostingViewModel: HostingViewModel = viewModel(
        factory = HostingViewModelFactory(context))

    var selectedHosting by remember {
        mutableStateOf<Hosting?>(null)
    }

    val hostings = remember {
        mutableStateListOf<Hosting>().apply {
            addAll(SampleHostings)
        }
    }
    var selectedConversation by remember {
        mutableStateOf<Conversation?>(null)
    }

    val hostingState by hostingViewModel.uiState.collectAsState()

    var profileSubScreen by remember {
        mutableStateOf(ProfileSubScreen.NONE)
    }

    LaunchedEffect(destination) {
        if (destination == GuestDestination.EXPLORE) {
            hostingViewModel.loadHostings()
        }
    }

    Scaffold(
        bottomBar = {
            SalvadorBottomBar(
                selectedIndex = destination.ordinal,
                onItemSelected = { index ->
                    selectedHosting = null
                    selectedConversation = null
                    profileSubScreen = ProfileSubScreen.NONE
                    destination = GuestDestination.entries[index]
                }
            )
        }
    ) { padding ->

        val detail = selectedHosting
        val conversation = selectedConversation

        if (detail != null) {
            HostingDetailScreen(
                hosting = detail,
                onBack = { selectedHosting = null },
                modifier = Modifier.padding(padding)
            )
        } else if (conversation != null) {
            ChatScreen(
                conversation = conversation,
                modifier = Modifier.padding(padding),
                onBack = { selectedConversation = null }
            )
        } else {

            when (profileSubScreen) {
                ProfileSubScreen.NOTIFICATIONS -> NotificationsScreen(
                    onBackClick = { profileSubScreen = ProfileSubScreen.NONE }
                )

                ProfileSubScreen.SECURITY -> PrivacySecurityScreen(
                    onBackClick = { profileSubScreen = ProfileSubScreen.NONE }
                )

                ProfileSubScreen.HELP -> HelpSupportScreen(
                    onBackClick = { profileSubScreen = ProfileSubScreen.NONE }
                )

                ProfileSubScreen.NONE -> {


                    when (destination) {
                        GuestDestination.HOME -> HomeScreen(
                            modifier = Modifier.padding(padding),
                            userRole = userRole,
                            onHostClick = onHostClick
                        )

                        GuestDestination.EXPLORE -> ExploreScreen(
                            hostings = hostingState.hostings,
                            modifier = Modifier.padding(padding),
                            onHostingClick = { hosting ->
                                selectedHosting = hosting
                            }
                        )

                        GuestDestination.MESSAGES -> MessagesScreen(
                            conversations = SampleConversations,
                            modifier = Modifier.padding(padding),
                            onBack = {
                                destination = GuestDestination.HOME
                            },
                            onConversationClick = { conversation ->
                                selectedConversation = conversation
                            }
                        )

                        GuestDestination.PUBLISH -> PublishHostingScreen(
                            modifier = Modifier.padding(padding),
                            onBack = {
                                destination = GuestDestination.HOME
                            },
                            onPublish = { title, location, description, pricePerNight, capacity, category, imageUri ->
                                hostingViewModel.publishHosting(
                                    title = title,
                                    location = location,
                                    description = description,
                                    pricePerNight = pricePerNight,
                                    capacity = capacity,
                                    category = category,
                                    imageUri,
                                    onSuccess = {
                                        destination = GuestDestination.HOME
                                    }
                                )
                            }
                        )

                        GuestDestination.BOOKINGS -> Box(
                            modifier = Modifier.padding(padding)
                        ) {
                            ReservationScreen(
                                property = ReservationProperty(
                                    imageRes = R.drawable.ic_menu_gallery,
                                    name = "Nombre del local",
                                    location = "La Libertad, El Salvador",
                                    pricePerNight = 45.0,
                                    maxGuests = 5,
                                    cleaningFee = 0.0,
                                    serviceFee = 0.0
                                ),
                                onBackClick = {
                                    destination = GuestDestination.HOME
                                },
                                onReservationConfirmed = {
                                    destination = GuestDestination.HOME
                                }
                            )
                        }

                        GuestDestination.PROFILE -> ProfileScreen(
                            modifier = Modifier.padding(padding),
                            onNotifications = {
                                profileSubScreen = ProfileSubScreen.NOTIFICATIONS
                            },
                            onSecurity = {
                                profileSubScreen = ProfileSubScreen.SECURITY
                            },
                            onHelp = {
                                profileSubScreen = ProfileSubScreen.HELP
                            }
                        )
                    }
                }
            }
        }
    }
}
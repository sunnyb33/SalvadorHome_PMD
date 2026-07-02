package com.example.salvadorhome.features.guest.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.salvadorhome.features.home.ui.HomeScreen
import com.example.salvadorhome.features.profile.ui.ProfileScreen
import com.example.salvadorhome.features.shared.ui.components.SalvadorBottomBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.salvadorhome.features.host.ui.screens.PublishHostingScreen
import com.example.salvadorhome.features.host.viewmodel.HostingViewModel
import com.example.salvadorhome.features.host.viewmodel.HostingViewModelFactory
import com.example.salvadorhome.features.profile.ui.HelpSupportScreen
import com.example.salvadorhome.features.profile.ui.NotificationsScreen
import com.example.salvadorhome.features.profile.ui.PrivacySecurityScreen
import com.example.salvadorhome.features.properties.ui.PropertyDetail
import com.example.salvadorhome.features.properties.ui.PropertyDetailScreen
import com.example.salvadorhome.features.reservations.ui.ReservationProperty
import com.example.salvadorhome.features.reservations.ui.ReservationScreen
import com.example.salvadorhome.features.shared.model.Conversation
import com.example.salvadorhome.features.shared.model.Hosting
import com.example.salvadorhome.features.shared.model.SampleConversations
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
        factory = HostingViewModelFactory(context)
    )

    var selectedHosting by remember {
        mutableStateOf<Hosting?>(null)
    }

    var reservingHosting by remember {
        mutableStateOf<Hosting?>(null)
    }

    var selectedConversation by remember {
        mutableStateOf<Conversation?>(null)
    }

    val hostingState by hostingViewModel.uiState.collectAsState()

    var profileSubScreen by remember {
        mutableStateOf(ProfileSubScreen.NONE)
    }

    LaunchedEffect(Unit) {
        hostingViewModel.loadHostings()
    }

    LaunchedEffect(destination) {
        if (destination == GuestDestination.EXPLORE) {
            hostingViewModel.loadHostings()
        }
    }

    Scaffold(
        bottomBar = {
            if (
                selectedHosting == null &&
                reservingHosting == null &&
                selectedConversation == null &&
                profileSubScreen == ProfileSubScreen.NONE
            ) {
                SalvadorBottomBar(
                    selectedIndex = destination.ordinal,
                    onItemSelected = { index ->
                        selectedHosting = null
                        selectedConversation = null
                        reservingHosting = null
                        profileSubScreen = ProfileSubScreen.NONE
                        destination = GuestDestination.entries[index]
                    }
                )
            }
        }
    ) { padding ->

        val detail = selectedHosting
        val conversation = selectedConversation
        val reserving = reservingHosting

        if (reserving != null) {
            ReservationScreen(
                property = ReservationProperty(
                    hostingId = reserving.id,
                    ownerId = reserving.ownerId,
                    imageUrl = reserving.imageUrls.firstOrNull() ?: "",
                    name = reserving.title,
                    location = reserving.location,
                    pricePerNight = reserving.price
                        .replace("$", "")
                        .replace("/ noche", "")
                        .replace(" ", "")
                        .toDoubleOrNull() ?: 0.0,
                    maxGuests = 5,
                    cleaningFee = 0.0,
                    serviceFee = 0.0
                ),
                onBackClick = {
                    reservingHosting = null
                },
                onReservationConfirmed = {
                    reservingHosting = null
                    destination = GuestDestination.BOOKINGS
                }
            )

        } else if (detail != null) {
            PropertyDetailScreen(
                property = PropertyDetail(
                    id = detail.id,
                    name = detail.title,
                    isCertified = true,
                    rating = 5.0f,
                    ownerName = "Propietario",
                    ownerRating = "Arrendador verificado",
                    pricePerNight = detail.price
                        .replace("$", "")
                        .replace("/ noche", "")
                        .replace(" ", "")
                        .toDoubleOrNull() ?: 0.0,
                    priceDescription = "Tarifa base por noche.",
                    description = detail.description,
                    highlights = listOf("Hospedaje publicado en SalvadorHouse"),
                    reviews = emptyList(),
                    ownerId = detail.ownerId,
                    imageUrl = detail.imageUrls.firstOrNull() ?: "",
                    bannerColors = detail.palette
                ),
                userRole = userRole,
                onBackClick = {
                    selectedHosting = null
                },
                onReserveClick = {
                    reservingHosting = detail
                }
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
                            hostings = hostingState.hostings,
                            userRole = userRole,
                            onHostClick = onHostClick,
                            onHostingClick = { hosting ->
                                selectedHosting = hosting
                            }
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
                            onPublish = { title, location, description, pricePerNight, capacity, category, imageUris ->

                                hostingViewModel.publishHosting(
                                    title = title,
                                    location = location,
                                    description = description,
                                    pricePerNight = pricePerNight,
                                    capacity = capacity,
                                    category = category,
                                    imageUris = imageUris,
                                    onSuccess = {
                                        hostingViewModel.loadHostings()
                                        destination = GuestDestination.HOME
                                    }
                                )
                            }
                        )

                        GuestDestination.BOOKINGS -> Box(
                            modifier = Modifier.padding(padding)
                        ) {
                            Text("Reservas pendientes")
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
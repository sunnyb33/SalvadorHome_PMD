package com.example.salvadorhome.features.reservations.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BookmarkAdded
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.People
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.salvadorhome.features.reservations.model.Reservation
import com.example.salvadorhome.features.reservations.viewmodel.ReservationViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun MyReservationsScreen(
    modifier: Modifier = Modifier,
    isHostMode: Boolean = false,
    viewModel: ReservationViewModel = viewModel()
) {
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        if (isHostMode) {
            viewModel.loadReservationsForMyHostings()
        } else {
            viewModel.loadMyReservations()
        }
    }

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 20.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Text(
                text = if (isHostMode) "Reservas recibidas" else "Mis reservas",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF0A1128)
            )

            Text(
                text = if (isHostMode)
                    "Reservas realizadas a tus publicaciones"
                else
                    "Hospedajes que has reservado",
                fontSize = 13.sp,
                color = Color.Gray
            )
        }

        if (state.reservations.isEmpty()) {
            item {
                Text(
                    text = "Aún no tienes reservas realizadas",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(top = 24.dp)
                )
            }
        } else {
            items(state.reservations, key = { it.id }) { reservation ->
                ReservationCard(reservation = reservation)
            }
        }
    }
}

@Composable
private fun ReservationCard(
    reservation: Reservation
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF8F9FA)
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.BookmarkAdded,
                    contentDescription = null,
                    tint = Color(0xFF0A1128)
                )

                Column(
                    modifier = Modifier.padding(start = 10.dp)
                ) {
                    Text(
                        text = reservation.hostingTitle,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF0A1128)
                    )

                    Text(
                        text = reservation.hostingLocation,
                        fontSize = 13.sp,
                        color = Color(0xFF0089AB)
                    )
                }
            }

            HorizontalDivider(color = Color(0xFFE0E0E0))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                InfoItem(
                    icon = Icons.Default.CalendarMonth,
                    title = "Entrada",
                    value = formatMillis(reservation.checkInMillis)
                )

                InfoItem(
                    icon = Icons.Default.CalendarMonth,
                    title = "Salida",
                    value = formatMillis(reservation.checkOutMillis)
                )
            }

            InfoItem(
                icon = Icons.Default.People,
                title = "Huéspedes",
                value = reservation.guests.toString()
            )

            Spacer(modifier = Modifier.height(4.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "${reservation.nights} noches",
                    fontSize = 14.sp,
                    color = Color.Gray
                )

                Text(
                    text = "$${reservation.total}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0A1128)
                )
            }

            Text(
                text = reservation.status,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF3357CC)
            )
        }
    }
}

@Composable
private fun InfoItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    value: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color(0xFF3357CC)
        )

        Column(
            modifier = Modifier.padding(start = 8.dp)
        ) {
            Text(
                text = title,
                fontSize = 11.sp,
                color = Color.Gray
            )

            Text(
                text = value,
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF0A1128)
            )
        }
    }
}

private fun formatMillis(millis: Long): String {
    if (millis == 0L) return "-"
    val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return formatter.format(Date(millis))
}
package com.example.salvadorhome.features.reservations.ui

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Preview(showBackground = true)
@Composable
fun ReservationSuccessScreen(
    onNavigateHome: () -> Unit = {}
) {

    val scale = remember { Animatable(0f) }

    LaunchedEffect(Unit) {

        scale.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 500)
        )

        delay(2000)
        onNavigateHome()
        // if (userRole == UserRole.ARRENDADOR) navController.navigate(NavRoutes.HOME)
        // else navController.navigate(NavRoutes.HOST_DASHBOARD)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.CheckCircle,
            contentDescription = "Reserva exitosa",
            tint = Color(0xFF2DB06A),
            modifier = Modifier
                .size(120.dp)
                .scale(scale.value)
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "¡Tu reserva se ha hecho con éxito!",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF0A1128)
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Redirigiendo al inicio...",
            fontSize = 13.sp,
            color = Color.Gray
        )
    }
}
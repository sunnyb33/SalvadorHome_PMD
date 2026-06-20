package com.example.salvadorhome.features.shared.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Apartment
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Kitchen
import androidx.compose.material.icons.filled.Tv
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.salvadorhome.core.theme.SalvadorHomeTheme
import com.example.salvadorhome.core.theme.SalvadorLavender

@Composable
fun HostingArtwork(colors: List<Color>, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.background(
            Brush.linearGradient(colors.ifEmpty { listOf(Color.Gray, Color.DarkGray) })
        ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            Icons.Default.Apartment,
            contentDescription = null,
            tint = Color.White.copy(alpha = .78f),
            modifier = Modifier.size(56.dp)
        )
    }
}

@Composable
fun ServicesGrid() {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            ServicePill("Wifi", Icons.Default.Wifi, Modifier.weight(1f))
            ServicePill("Cocina", Icons.Default.Kitchen, Modifier.weight(1f))
        }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            ServicePill("TV", Icons.Default.Tv, Modifier.weight(1f))
            ServicePill("Estacionamiento", Icons.Default.DirectionsCar, Modifier.weight(1f))
        }
    }
}

@Composable
private fun ServicePill(label: String, icon: ImageVector, modifier: Modifier = Modifier) {
    Surface(modifier = modifier, shape = RoundedCornerShape(50), color = SalvadorLavender) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(icon, contentDescription = null, modifier = Modifier.size(16.dp))
            Spacer(Modifier.width(5.dp))
            Text(label, fontSize = 11.sp, maxLines = 1)
        }
    }
}

@Preview(name = "Imagen temporal", showBackground = true, widthDp = 360)
@Composable
private fun HostingArtworkPreview() {
    SalvadorHomeTheme {
        HostingArtwork(
            colors = listOf(Color(0xFFD7B28C), Color(0xFF78543C)),
            modifier = Modifier
                .fillMaxWidth()
                .height(190.dp)
        )
    }
}

@Preview(name = "Servicios incluidos", showBackground = true, widthDp = 360)
@Composable
private fun ServicesGridPreview() {
    SalvadorHomeTheme {
        Surface {
            ServicesGrid()
        }
    }
}

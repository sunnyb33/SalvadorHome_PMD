package com.example.salvadorhome.features.home.ui

import androidx.compose.foundation.Image
import androidx.compose.material3.Button
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.salvadorhome.features.shared.model.Hosting
import com.example.salvadorhome.features.shared.ui.components.HostingArtwork
import com.example.salvadorhome.R

@Preview
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    hostings: List<Hosting> = emptyList(),
    userRole: String = "",
    onHostClick: () -> Unit = {},
    onHostingClick: (Hosting) -> Unit = {}
) {
    val MainColor = Color(0xFF0A1128)
    val ChipSelectedBg = Color(0xFF0A1128)
    val ChipTextColor = Color(0xFFF5F5F5)

    var selectedCategory by remember {
        mutableStateOf("TODOS")
    }

    val filteredHostings = remember(selectedCategory, hostings) {
        if (selectedCategory == "TODOS") {
            hostings
        } else {
            hostings.filter {
                it.category.equals(selectedCategory, ignoreCase = true)
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 24.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Bienvenido",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = MainColor
                )

                Text(
                    text = "¿A dónde deseas hospedarte?",
                    fontSize = 13.sp,
                    color = Color.Gray
                )

                if (userRole.equals("Ambos", ignoreCase = true)) {
                    Spacer(modifier = Modifier.height(10.dp))

                    Button(onClick = onHostClick) {
                        Text("Ir a modo anfitrión")
                    }
                }
            }

            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFE0E0E0)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = android.R.drawable.ic_menu_myplaces),
                    contentDescription = "Perfil",
                    tint = MainColor,
                    modifier = Modifier.size(24.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .clip(RoundedCornerShape(16.dp)),
            contentAlignment = Alignment.BottomStart
        ) {
            Image(
                painterResource(id = R.drawable.bannersegundo),
                contentDescription = "Banner El Salvador",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.35f))
            )

            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "🇸🇻 +${hostings.size} destinos certificados",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = "en todo El Salvador",
                    fontSize = 13.sp,
                    color = Color.White.copy(alpha = 0.9f)
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier.horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            listOf("TODOS", "PLAYA", "NATURALEZA", "HOTEL", "MONTAÑA").forEach { category ->
                val isSelected = selectedCategory == category

                FilterChip(
                    selected = isSelected,
                    onClick = { selectedCategory = category },
                    label = {
                        Text(
                            text = category.lowercase().replaceFirstChar { it.uppercase() },
                            fontSize = 13.sp
                        )
                    },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = ChipSelectedBg,
                        selectedLabelColor = ChipTextColor,
                        containerColor = Color(0xFFF0F0F0),
                        labelColor = MainColor
                    ),
                    border = FilterChipDefaults.filterChipBorder(
                        enabled = true,
                        selected = isSelected,
                        borderColor = Color.Transparent,
                        selectedBorderColor = Color.Transparent
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Destacados",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = MainColor
        )

        Spacer(modifier = Modifier.height(12.dp))

        if (filteredHostings.isEmpty()) {
            Text(
                text = "No hay publicaciones disponibles",
                fontSize = 13.sp,
                color = Color.Gray,
                modifier = Modifier.padding(vertical = 20.dp)
            )
        } else {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                filteredHostings.forEach { hosting ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0xFFF5F5F5))
                            .clickable { onHostingClick(hosting) }
                            .padding(8.dp)
                    ) {
                        HostingArtwork(
                            colors = hosting.palette,
                            imageUrl = hosting.imageUrls.firstOrNull(),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(180.dp)
                                .clip(RoundedCornerShape(12.dp))
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = hosting.title,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 14.sp,
                            color = MainColor
                        )

                        Text(
                            text = hosting.location,
                            fontSize = 12.sp,
                            color = Color(0xFF0089AB)
                        )

                        Text(
                            text = hosting.price,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = MainColor
                        )
                    }
                }
            }
        }
    }
}
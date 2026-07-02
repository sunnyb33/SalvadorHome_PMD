package com.example.salvadorhome.features.host.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.salvadorhome.core.theme.SalvadorHomeTheme
import com.example.salvadorhome.core.theme.SalvadorNavy
import com.example.salvadorhome.core.theme.SalvadorOutline
import com.example.salvadorhome.core.theme.SalvadorTextSecondary
import com.example.salvadorhome.features.shared.model.Hosting
import com.example.salvadorhome.features.shared.model.SampleHostings
import com.example.salvadorhome.features.shared.ui.components.HostingArtwork
import com.example.salvadorhome.features.shared.ui.components.SalvadorBottomBar
import com.example.salvadorhome.features.shared.ui.components.ServicesGrid

@Composable
fun HostingDetailScreen(
    hosting: Hosting,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    onEditClick: (Hosting) -> Unit = {},
    onDeleteClick: (Hosting) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 28.dp)
    ) {
            item {
                androidx.compose.foundation.layout.Box {
                    HostingArtwork(
                        colors = hosting.palette,
                        imageUrl = hosting.imageUrls.firstOrNull(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(310.dp)
                    )
                    IconButton(onClick = onBack, modifier = Modifier.padding(16.dp).background(Color.White.copy(.9f), CircleShape)) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Volver")
                    }
                }
            }
            item {
                Column(modifier = Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text(hosting.title, fontSize = 26.sp, fontWeight = FontWeight.Bold)
                    Text(hosting.location, color = SalvadorTextSecondary)
                    Row { Icon(Icons.Default.Star, null, tint = Color(0xFFFFB300)); Text(" 4.8 · 24 reseñas", fontWeight = FontWeight.SemiBold) }
                    HorizontalDivider(color = SalvadorOutline)
                    Text("Descripción", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                    Text(hosting.description, lineHeight = 22.sp)
                    Text("Servicios incluidos", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                    ServicesGrid()
                    Button(
                        onClick = { onEditClick(hosting) },
                        modifier = Modifier.fillMaxWidth().height(52.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = SalvadorNavy)
                    ) {
                        Text("Editar publicación")
                    }

                    Button(
                        onClick = { onDeleteClick(hosting) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFD32F2F)
                        )
                    ) {
                        Text("Eliminar publicación")
                    }

                }
            }
    }
}

@Preview(
    name = "Detalle del hospedaje",
    showBackground = true,
    widthDp = 390,
    heightDp = 844
)
@Composable
private fun HostingDetailScreenPreview() {
    SalvadorHomeTheme {
        Scaffold(
            bottomBar = {
                SalvadorBottomBar(selectedIndex = 0, onItemSelected = {})
            }
        ) { padding ->
            HostingDetailScreen(
                hosting = SampleHostings.first(),
                onBack = {},
                modifier = Modifier.padding(padding)
            )
        }
    }
}

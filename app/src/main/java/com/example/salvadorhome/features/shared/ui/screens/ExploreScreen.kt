package com.example.salvadorhome.features.shared.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.salvadorhome.core.theme.SalvadorHomeTheme
import com.example.salvadorhome.core.theme.SalvadorLavender
import com.example.salvadorhome.core.theme.SalvadorNavy
import com.example.salvadorhome.core.theme.SalvadorOutline
import com.example.salvadorhome.core.theme.SalvadorTextSecondary
import com.example.salvadorhome.features.shared.model.Hosting
import com.example.salvadorhome.features.shared.model.SampleHostings
import com.example.salvadorhome.features.shared.ui.components.HostingArtwork
import com.example.salvadorhome.features.shared.ui.components.SalvadorBottomBar

/** Pantalla disponible para huésped y arrendador. */
@Composable
fun ExploreScreen(
    hostings: List<Hosting>,
    modifier: Modifier = Modifier,
    onHostingClick: (Hosting) -> Unit
) {
    var selectedFilter by remember { mutableStateOf("Todos") }
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 18.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        item {
            Text("Bienvenido", fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Text("¿A dónde deseas hospedarte?", color = SalvadorTextSecondary, fontSize = 13.sp)
        }
        item {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                listOf("Todos", "Playa", "Naturaleza", "Hotel").forEach { filter ->
                    Surface(
                        color = if (selectedFilter == filter) SalvadorLavender else Color.Transparent,
                        shape = RoundedCornerShape(50),
                        border = BorderStroke(1.dp, SalvadorOutline),
                        modifier = Modifier.clickable { selectedFilter = filter }
                    ) {
                        Text(filter, fontSize = 12.sp, modifier = Modifier.padding(horizontal = 13.dp, vertical = 7.dp))
                    }
                }
            }
        }
        item { Text("Destacados", fontWeight = FontWeight.SemiBold, fontSize = 17.sp) }
        items(hostings, key = { it.title }) { hosting ->
            Column(modifier = Modifier.clickable { onHostingClick(hosting) }) {
                Box {
                    HostingArtwork(
                        colors = hosting.palette,
                        modifier = Modifier.fillMaxWidth().aspectRatio(1.75f).clip(RoundedCornerShape(24.dp))
                    )
                    Icon(
                        Icons.Default.FavoriteBorder,
                        contentDescription = "Guardar",
                        tint = Color.White,
                        modifier = Modifier.align(Alignment.TopEnd).padding(14.dp)
                            .background(Color.Black.copy(alpha = .25f), CircleShape).padding(7.dp)
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth().padding(top = 9.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text("${hosting.title} · ${hosting.location}", fontWeight = FontWeight.SemiBold)
                        Text(hosting.price, color = SalvadorTextSecondary, fontSize = 13.sp)
                    }
                    Icon(Icons.Default.StarBorder, contentDescription = null)
                }
                HorizontalDivider(modifier = Modifier.padding(top = 12.dp), color = SalvadorOutline)
            }
        }
    }
}

@Preview(
    name = "Explorar hospedajes - ambos roles",
    showBackground = true,
    widthDp = 390,
    heightDp = 844
)
@Composable
private fun ExploreScreenPreview() {
    SalvadorHomeTheme {
        Scaffold(
            bottomBar = {
                SalvadorBottomBar(selectedIndex = 1, onItemSelected = {})
            }
        ) { padding ->
            ExploreScreen(
                hostings = SampleHostings,
                modifier = Modifier.padding(padding),
                onHostingClick = {}
            )
        }
    }
}

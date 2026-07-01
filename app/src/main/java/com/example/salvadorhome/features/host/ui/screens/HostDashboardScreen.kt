package com.example.salvadorhome.features.host.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.salvadorhome.core.theme.SalvadorHomeTheme
import com.example.salvadorhome.core.theme.SalvadorBlue
import com.example.salvadorhome.core.theme.SalvadorLavender
import com.example.salvadorhome.core.theme.SalvadorLavenderLight
import com.example.salvadorhome.core.theme.SalvadorNavy
import com.example.salvadorhome.core.theme.SalvadorOutline
import com.example.salvadorhome.core.theme.SalvadorTextSecondary
import com.example.salvadorhome.features.shared.model.Hosting
import com.example.salvadorhome.features.shared.model.SampleHostings
import com.example.salvadorhome.features.shared.ui.components.HostingArtwork
import com.example.salvadorhome.features.shared.ui.components.SalvadorBottomBar

@Composable
fun HostDashboardScreen(
    hostings: List<Hosting>,
    modifier: Modifier = Modifier,
    onHostingClick: (Hosting) -> Unit,
    onPublishClick: () -> Unit
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 18.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Column(modifier = Modifier.weight(1f)) {
                    Text("Bienvenido", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                    Text("Vamos como va el negocio", color = SalvadorTextSecondary, fontSize = 13.sp)
                }
                Surface(shape = CircleShape, color = Color(0xFF8E83FF), modifier = Modifier.size(42.dp)) {
                    Icon(Icons.Default.Person, "Perfil", tint = Color.White, modifier = Modifier.padding(8.dp))
                }
            }
        }
        item {
            Row(modifier = Modifier.fillMaxWidth().padding(top = 12.dp)) {
                Text("Tus publicaciones", fontSize = 17.sp, fontWeight = FontWeight.SemiBold, modifier = Modifier.weight(1f))
                Text("+ Nueva", color = SalvadorNavy, fontWeight = FontWeight.Bold, modifier = Modifier.clickable(onClick = onPublishClick))
            }
        }
        items(hostings, key = { it.id }) { hosting -> HostingRow(hosting) { onHostingClick(hosting) } }
        item { Text("Estadísticas", fontSize = 17.sp, fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(top = 8.dp)) }
        item { StatisticsCard(hostings.size) }
    }
}

@Composable
private fun HostingRow(hosting: Hosting, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().height(82.dp).clickable(onClick = onClick),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = SalvadorLavenderLight),
        border = BorderStroke(1.dp, SalvadorOutline)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Surface(color = SalvadorLavender, shape = CircleShape, modifier = Modifier.padding(start = 14.dp).size(38.dp)) {
                Box(contentAlignment = Alignment.Center) {
                    Text(hosting.title.first().uppercase(), color = Color(0xFF6E5BC5), fontWeight = FontWeight.Bold)
                }
            }
            Column(modifier = Modifier.weight(1f).padding(horizontal = 12.dp)) {
                Text(hosting.title, fontWeight = FontWeight.SemiBold, maxLines = 1, overflow = TextOverflow.Ellipsis)
                Text("${hosting.status} · ${hosting.location}", color = SalvadorTextSecondary, fontSize = 11.sp, maxLines = 1)
            }
            HostingArtwork(hosting.palette, imageUrl = hosting.imageUrls.firstOrNull(),Modifier.width(86.dp).fillMaxHeight())
        }
    }
}

@Composable
private fun StatisticsCard(publications: Int) {
    Card(
        modifier = Modifier.fillMaxWidth().height(150.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = SalvadorBlue),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Text("Calificación", fontSize = 13.sp, modifier = Modifier.weight(1f))
                repeat(5) { Icon(Icons.Default.StarBorder, null, modifier = Modifier.size(22.dp), tint = SalvadorNavy) }
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Publicaciones activas", fontSize = 13.sp)
                Spacer(Modifier.width(12.dp))
                Surface(color = SalvadorNavy, shape = RoundedCornerShape(4.dp)) {
                    Text(publications.toString(), color = Color.White, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 9.dp, vertical = 4.dp))
                }
            }
        }
    }
}

@Preview(
    name = "Inicio del arrendador",
    showBackground = true,
    widthDp = 390,
    heightDp = 844
)
@Composable
private fun HostDashboardScreenPreview() {
    SalvadorHomeTheme {
        Scaffold(
            bottomBar = {
                SalvadorBottomBar(selectedIndex = 0, onItemSelected = {})
            }
        ) { padding ->
            HostDashboardScreen(
                hostings = SampleHostings,
                modifier = Modifier.padding(padding),
                onHostingClick = {},
                onPublishClick = {}
            )
        }
    }
}

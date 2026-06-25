package com.example.salvadorhome.features.host.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.salvadorhome.core.theme.SalvadorHomeTheme
import com.example.salvadorhome.core.theme.SalvadorNavy
import com.example.salvadorhome.core.theme.SalvadorOutline
import com.example.salvadorhome.core.theme.SalvadorTextSecondary
import com.example.salvadorhome.features.shared.ui.components.ServicesGrid
import com.example.salvadorhome.features.shared.ui.components.SalvadorBottomBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PublishHostingScreen(
    modifier: Modifier = Modifier,
    onBack: () -> Unit,
    onPublish: (String, String, String) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("San Salvador") }
    var description by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    var termsAccepted by remember { mutableStateOf(false) }
    val locations = listOf("San Salvador", "La Libertad", "Santa Ana", "Sonsonate", "Ahuachapán")
    var pricePerNight by remember { mutableStateOf("") }
    var capacity by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("PLAYA") }
    var categoryExpanded by remember { mutableStateOf(false) }
    val categories = listOf(
        "PLAYA",
        "NATURALEZA",
        "HOTEL",
        "MONTAÑA"
    )

    LazyColumn(
        modifier = modifier.fillMaxSize().imePadding(),
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onBack, modifier = Modifier.background(SalvadorNavy, CircleShape)) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, "Volver", tint = Color.White)
                }
                Text("¿Deseas publicar un nuevo\nhospedaje?", fontWeight = FontWeight.SemiBold, fontSize = 18.sp, modifier = Modifier.padding(start = 14.dp))
            }
        }
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = Color(0xFFEEF0FF)),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, SalvadorOutline)
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text("Hospedaje", fontSize = 22.sp, fontWeight = FontWeight.Bold)
                    Text("Completa la información para crear tu publicación", color = SalvadorTextSecondary, fontSize = 12.sp)
                    Label("Título")
                    OutlinedTextField(
                        value = title, onValueChange = { title = it }, modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("Ej. Casa frente al mar") }, singleLine = true,
                        keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences)
                    )
                    Label("Ubicación")
                    ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded }) {
                        OutlinedTextField(
                            value = location, onValueChange = {}, readOnly = true,
                            modifier = Modifier
                                .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable)
                                .fillMaxWidth(),
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) }
                        )
                        ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                            locations.forEach {
                                DropdownMenuItem(text = { Text(it) }, onClick = { location = it; expanded = false })
                            }
                        }
                    }
                    Label("Descripción")
                    OutlinedTextField(
                        value = description, onValueChange = { description = it },
                        modifier = Modifier.fillMaxWidth().height(120.dp),
                        placeholder = { Text("Describe tu hospedaje") }
                    )
                    Label("Precio por noche")
                    OutlinedTextField(
                        value = pricePerNight,
                        onValueChange = { pricePerNight = it },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("Ej. 50") },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        )
                    )
                    Label("Capacidad")
                    OutlinedTextField(
                        value = capacity,
                        onValueChange = { capacity = it },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("Ej. 4 huéspedes") },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        )
                    )
                    Label("Categoría")
                    ExposedDropdownMenuBox(
                        expanded = categoryExpanded,
                        onExpandedChange = {
                            categoryExpanded = !categoryExpanded
                        }
                    ) {

                        OutlinedTextField(
                            value = category,
                            onValueChange = {},
                            readOnly = true,
                            modifier = Modifier
                                .menuAnchor(
                                    ExposedDropdownMenuAnchorType.PrimaryNotEditable
                                )
                                .fillMaxWidth(),
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(
                                    expanded = categoryExpanded
                                )
                            }
                        )

                        ExposedDropdownMenu(
                            expanded = categoryExpanded,
                            onDismissRequest = {
                                categoryExpanded = false
                            }
                        ) {
                            categories.forEach {
                                DropdownMenuItem(
                                    text = { Text(it) },
                                    onClick = {
                                        category = it
                                        categoryExpanded = false
                                    }
                                )
                            }
                        }
                    }
                    Label("Imágenes del hospedaje")
                    Surface(
                        modifier = Modifier.fillMaxWidth().height(100.dp).clickable { },
                        shape = RoundedCornerShape(12.dp), color = Color.White,
                        border = BorderStroke(1.dp, SalvadorOutline)
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                            Icon(Icons.Default.AddAPhoto, null)
                            Text("Agregar fotografías", fontSize = 12.sp, color = SalvadorTextSecondary)
                        }
                    }
                    Label("Servicios incluidos")
                    ServicesGrid()
                    Label("Etiquetas destacadas")
                    listOf("Ideal para familias", "Cerca de lugares turísticos", "Zona tranquila").forEach {
                        Text("•  $it", color = Color(0xFF3454B4), fontWeight = FontWeight.SemiBold)
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(termsAccepted, onCheckedChange = { termsAccepted = it })
                        Text("Acepto los términos y condiciones", fontSize = 12.sp)
                    }
                    Button(
                        onClick = { onPublish(title, location, description) },
                        enabled = termsAccepted && title.isNotBlank(),
                        modifier = Modifier.fillMaxWidth().height(50.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = SalvadorNavy)
                    ) { Text("Publicar") }
                }
            }
        }
    }
}

@Composable
private fun Label(text: String) = Text(text, fontWeight = FontWeight.Medium, fontSize = 13.sp)

@Preview(
    name = "Publicar hospedaje",
    showBackground = true,
    widthDp = 390,
    heightDp = 844
)
@Composable
private fun PublishHostingScreenPreview() {
    SalvadorHomeTheme {
        Scaffold(
            bottomBar = {
                SalvadorBottomBar(selectedIndex = 3, onItemSelected = {})
            }
        ) { padding ->
            PublishHostingScreen(
                modifier = Modifier.padding(padding),
                onBack = {},
                onPublish = { _, _, _ -> }
            )
        }
    }
}

package com.example.salvadorhome.features.host.ui.screens

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.background
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.salvadorhome.core.theme.SalvadorNavy
import com.example.salvadorhome.core.theme.SalvadorOutline
import com.example.salvadorhome.core.theme.SalvadorTextSecondary
import com.example.salvadorhome.features.shared.model.Hosting

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditHostingScreen(
    hosting: Hosting,
    modifier: Modifier = Modifier,
    onBack: () -> Unit,
    onSave: (
        title: String,
        location: String,
        description: String,
        pricePerNight: Double,
        capacity: Int,
        category: String
    ) -> Unit
) {
    var title by remember { mutableStateOf(hosting.title) }
    var location by remember { mutableStateOf(hosting.location) }
    var description by remember { mutableStateOf(hosting.description) }

    var pricePerNight by remember {
        mutableStateOf(
            hosting.price
                .replace("$", "")
                .replace("/ noche", "")
                .replace(" ", "")
        )
    }

    var capacity by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("PLAYA") }

    var locationExpanded by remember { mutableStateOf(false) }
    var categoryExpanded by remember { mutableStateOf(false) }

    val locations = listOf(
        "San Salvador",
        "La Libertad",
        "Santa Ana",
        "Sonsonate",
        "Ahuachapán"
    )

    val categories = listOf(
        "PLAYA",
        "NATURALEZA",
        "HOTEL",
        "MONTAÑA"
    )

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .imePadding(),
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(
                    onClick = onBack,
                    modifier = Modifier.background(SalvadorNavy, CircleShape)
                ) {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Volver",
                        tint = androidx.compose.ui.graphics.Color.White
                    )
                }

                Text(
                    text = "Editar publicación",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(start = 14.dp)
                )
            }
        }

        item {
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = androidx.compose.ui.graphics.Color(0xFFEEF0FF)
                ),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, SalvadorOutline)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Hospedaje",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "Modifica la información de tu publicación",
                        color = SalvadorTextSecondary,
                        fontSize = 12.sp
                    )

                    Label("Título")
                    OutlinedTextField(
                        value = title,
                        onValueChange = { title = it },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("Ej. Casa frente al mar") },
                        singleLine = true
                    )

                    Label("Ubicación")
                    ExposedDropdownMenuBox(
                        expanded = locationExpanded,
                        onExpandedChange = { locationExpanded = !locationExpanded }
                    ) {
                        OutlinedTextField(
                            value = location,
                            onValueChange = {},
                            readOnly = true,
                            modifier = Modifier
                                .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable)
                                .fillMaxWidth(),
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(locationExpanded)
                            }
                        )

                        ExposedDropdownMenu(
                            expanded = locationExpanded,
                            onDismissRequest = { locationExpanded = false }
                        ) {
                            locations.forEach {
                                DropdownMenuItem(
                                    text = { Text(it) },
                                    onClick = {
                                        location = it
                                        locationExpanded = false
                                    }
                                )
                            }
                        }
                    }

                    Label("Descripción")
                    OutlinedTextField(
                        value = description,
                        onValueChange = { description = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp),
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
                        onExpandedChange = { categoryExpanded = !categoryExpanded }
                    ) {
                        OutlinedTextField(
                            value = category,
                            onValueChange = {},
                            readOnly = true,
                            modifier = Modifier
                                .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable)
                                .fillMaxWidth(),
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(categoryExpanded)
                            }
                        )

                        ExposedDropdownMenu(
                            expanded = categoryExpanded,
                            onDismissRequest = { categoryExpanded = false }
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

                    Button(
                        onClick = {
                            onSave(
                                title,
                                location,
                                description,
                                pricePerNight.toDoubleOrNull() ?: 0.0,
                                capacity.toIntOrNull() ?: 0,
                                category
                            )
                        },
                        enabled = title.isNotBlank() &&
                                location.isNotBlank() &&
                                description.isNotBlank() &&
                                pricePerNight.toDoubleOrNull() != null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = SalvadorNavy
                        )
                    ) {
                        Text("Guardar cambios")
                    }
                }
            }
        }
    }
}

@Composable
private fun Label(text: String) {
    Text(
        text = text,
        fontWeight = FontWeight.Medium,
        fontSize = 13.sp
    )
}
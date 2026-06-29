package com.example.salvadorhome.features.auth.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.salvadorhome.R
import com.example.salvadorhome.features.auth.ui.components.RoleOption
@Preview
@Composable
fun RoleSelectionScreen(
    onRoleSelected: (String) -> Unit = {}
) {
    val TextColor = Color(0xFF0A1128)
    val MainColor = Color(0xFF0A1128)

    var selectedRole by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F9FA))
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "¿Cómo deseas usar la app?",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = TextColor
        )

        Spacer(modifier = Modifier.height(32.dp))

        RoleOption(
            icon = R.drawable.icono_arrendador,
            title = "Quiero hospedar",
            description = "Publica tus propiedades y gana dinero",
            isSelected = selectedRole == "Arrendador",
            onClick = {
                selectedRole = "Arrendador"
                showError = false
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        RoleOption(
            icon = R.drawable.arrendatario_icon,
            title = "Quiero viajar",
            description = "Encuentra y reserva alojamientos",
            isSelected = selectedRole == "Arrendatario",
            onClick = {
                selectedRole = "Arrendatario"
                showError = false
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        RoleOption(
            icon = R.drawable.ambos_rol_icon,
            title = "Ambos",
            description = "Publica hospedajes y también reserva alojamientos",
            isSelected = selectedRole == "Ambos",
            onClick = {
                selectedRole = "Ambos"
                showError = false
            }
        )

        Spacer(modifier = Modifier.height(32.dp))

        if (showError) {
            Text(
                text = "Por favor selecciona un rol para continuar",
                color = Color.Red,
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        Button(
            onClick = {
                if (selectedRole.isNotEmpty()) {
                    onRoleSelected(selectedRole)
                } else {
                    showError = true
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MainColor),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text("Continuar", color = Color.White, fontSize = 16.sp)
        }
    }
}
package com.example.salvadorhome.features.profile.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview(showBackground = true)
@Composable
fun PrivacySecurityScreen(
    onBackClick: () -> Unit = {}
    // viewModel: ProfileViewModel = viewModel()
) {
    val MainColor     = Color(0xFF0A1128)
    val LavenderColor = Color(0xFFE8E8F8)

    //Temporal
    var currentPassword  by remember { mutableStateOf("") }
    var newPassword      by remember { mutableStateOf("") }
    var confirmPassword  by remember { mutableStateOf("") }
    val passwordMismatch = newPassword.isNotBlank() && confirmPassword.isNotBlank() && newPassword != confirmPassword

    Column(modifier = Modifier.fillMaxSize()) {

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(0.dp),
            colors = CardDefaults.cardColors(containerColor = LavenderColor),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Volver",
                        tint = MainColor
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Privacidad y Seguridad",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MainColor
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Cambiar contraseña",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = MainColor
                    )
                    Text(
                        text = "Tu contraseña debe tener al menos 8 caracteres",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )

                    OutlinedTextField(
                        value = currentPassword,
                        onValueChange = { currentPassword = it },
                        label = { Text("Contraseña actual") },
                        visualTransformation = PasswordVisualTransformation(),
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        shape = RoundedCornerShape(8.dp)
                    )

                    OutlinedTextField(
                        value = newPassword,
                        onValueChange = { newPassword = it },
                        label = { Text("Nueva contraseña") },
                        visualTransformation = PasswordVisualTransformation(),
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        shape = RoundedCornerShape(8.dp)
                    )

                    OutlinedTextField(
                        value = confirmPassword,
                        onValueChange = { confirmPassword = it },
                        label = { Text("Confirmar nueva contraseña") },
                        visualTransformation = PasswordVisualTransformation(),
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        isError = passwordMismatch,
                        shape = RoundedCornerShape(8.dp)
                    )

                    if (passwordMismatch) {
                        Text(
                            text = "Las contraseñas no coinciden",
                            color = Color(0xFFE53935),
                            fontSize = 12.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Button(
                        onClick = { /* Cambiar por la logica cuando ya este */ },
                        enabled = currentPassword.isNotBlank() && newPassword.isNotBlank() && confirmPassword.isNotBlank() && !passwordMismatch,
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MainColor,
                            disabledContainerColor = Color(0xFFCCCCCC)
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                    ) {
                        Text("Guardar cambios", color = Color.White, fontWeight = FontWeight.Bold)
                    }
                }
            }

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = LavenderColor),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "🔒 Tu información está segura",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp,
                        color = MainColor
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "SalvadorHouse protege tus datos personales y nunca comparte tu información con terceros sin tu consentimiento.",
                        fontSize = 12.sp,
                        color = Color.DarkGray,
                        lineHeight = 18.sp
                    )
                }
            }
        }
    }
}
package com.example.salvadorhome.features.auth.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun RegisterScreen(
    onContinueToRole: (
        email: String,
        password: String,
        nombre: String,
        apellido: String
    ) -> Unit = { _, _, _, _ -> }
) {
    RegisterScreenContent(
        onContinueToRole = onContinueToRole
    )
}

@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {
    RegisterScreenContent(
        onContinueToRole = { _, _, _, _ -> }
    )
}

@Composable
fun RegisterScreenContent(
    onContinueToRole: (
        email: String,
        password: String,
        nombre: String,
        apellido: String
    ) -> Unit
) {
    val TextColor = Color(0xFF0A1128)
    val MainColor = Color(0xFF0A1128)
    val ButtonTextColor = Color(0xFFF5F5F5)

    var nombre by remember { mutableStateOf("") }
    var apellido by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    var errorMessage by remember { mutableStateOf<String?>(null) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(32.dp, 38.dp)
            .background(Color(0xFFF8F9FA))
    ) {
        Text(
            text = "¡Bienvenido!",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.SansSerif,
            color = TextColor
        )

        Spacer(modifier = Modifier.height(45.dp))

        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre", fontFamily = FontFamily.SansSerif) },
            placeholder = { Text("Nombre", fontFamily = FontFamily.SansSerif) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = apellido,
            onValueChange = { apellido = it },
            label = { Text("Apellido", fontFamily = FontFamily.SansSerif) },
            placeholder = { Text("Apellido", fontFamily = FontFamily.SansSerif) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email", fontFamily = FontFamily.SansSerif) },
            placeholder = { Text("ejemplo@correo.com", fontFamily = FontFamily.SansSerif) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña", fontFamily = FontFamily.SansSerif) },
            placeholder = { Text("Contraseña", fontFamily = FontFamily.SansSerif) },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text("Confirmar contraseña", fontFamily = FontFamily.SansSerif) },
            placeholder = { Text("ejemplo12345") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth(),
            isError = errorMessage != null
        )

        errorMessage?.let {
            Text(
                text = it,
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier
                    .padding(top = 4.dp)
                    .align(Alignment.Start)
            )
        }

        Spacer(modifier = Modifier.height(40.dp))

        Button(
            onClick = {
                errorMessage = null

                when {
                    nombre.isBlank() || apellido.isBlank() || email.isBlank() -> {
                        errorMessage = "Por favor llena todos los campos"
                    }

                    password.length < 6 -> {
                        errorMessage = "La contraseña debe tener al menos 6 caracteres"
                    }

                    password != confirmPassword -> {
                        errorMessage = "Las contraseñas no coinciden"
                    }

                    else -> {
                        onContinueToRole(
                            email,
                            password,
                            nombre,
                            apellido
                        )
                    }
                }
            },
            shape = RoundedCornerShape(8.dp),
            contentPadding = PaddingValues(vertical = 4.dp, horizontal = 80.dp),
            colors = ButtonDefaults.buttonColors(MainColor),
            modifier = Modifier
                .width(250.dp)
                .height(48.dp)
        ) {
            Text(
                text = "Confirmar",
                color = ButtonTextColor,
                fontSize = 18.sp
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}
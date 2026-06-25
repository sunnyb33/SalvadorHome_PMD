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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.salvadorhome.features.auth.viewmodel.AuthState
import com.example.salvadorhome.features.auth.viewmodel.AuthViewModel
import com.example.salvadorhome.features.auth.viewmodel.AuthViewModelFactory


@Composable
fun RegisterScreen(
    onRegisterSuccess: () -> Unit = {},
    viewModel: AuthViewModel = viewModel(factory = AuthViewModelFactory())
) {
    val authState by viewModel.authState.collectAsState()

    LaunchedEffect(authState) {
        if (authState is AuthState.Success) {
            onRegisterSuccess()
            viewModel.resetState()
        }
    }

    RegisterScreenContent(
        authState = authState,
        // NUEVO: Agregamos nombre y apellido a la firma del evento
        onRegisterClick = { email, password, nombre, apellido, rol ->
            viewModel.register(email, password, nombre, apellido, rol)
        }
    )
}

@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {
    RegisterScreenContent(
        authState = AuthState.Idle,
        // NUEVO: Actualizamos el preview con los nuevos parámetros vacíos
        onRegisterClick = { _, _, _, _, _ -> }
    )
}

@Composable
fun RegisterScreenContent(
    authState: AuthState,
    // NUEVO: Actualizamos la función para recibir 5 Strings en lugar de 3
    onRegisterClick: (String, String, String, String, String) -> Unit
) {
    val TextColor = Color(0xFF0A1128)
    val MainColor = Color(0xFF0A1128)
    val ButtonTextColor = Color(0xFFF5F5F5)

    var nombre by remember { mutableStateOf("") }
    var apellido by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var Confirmpassword by remember { mutableStateOf("") }

    var passwordError by remember { mutableStateOf<String?>(null) }

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
            label = {Text("Nombre", fontFamily = FontFamily.SansSerif)},
            placeholder = {Text("Nombre", fontFamily = FontFamily.SansSerif)},
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = apellido,
            onValueChange = { apellido = it },
            label = {Text("Apellido", fontFamily = FontFamily.SansSerif)},
            placeholder = {Text("Apellido", fontFamily = FontFamily.SansSerif)},
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = {Text("Email", fontFamily = FontFamily.SansSerif)},
            placeholder = {Text("ejemplo@correo.com", fontFamily = FontFamily.SansSerif)},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = {Text("Contraseña", fontFamily = FontFamily.SansSerif)},
            placeholder = {Text("Contraseña", fontFamily = FontFamily.SansSerif)},
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = Confirmpassword,
            onValueChange = {Confirmpassword = it},
            label = {Text("Confirmar contraseña", fontFamily = FontFamily.SansSerif)},
            placeholder = {Text("ejemplo12345")},
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth(),
            isError = passwordError != null
        )

        if (passwordError != null) {
            Text(
                text = passwordError!!,
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier.padding(top = 4.dp).align(Alignment.Start)
            )
        }

        if (authState is AuthState.Error) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = (authState as AuthState.Error).message,
                color = Color.Red,
                fontSize = 13.sp
            )
        }

        Spacer(modifier = Modifier.height(40.dp))

        Button(
            onClick = {
                passwordError = null
                if (password.length < 6) {
                    passwordError = "La contraseña debe tener al menos 6 caracteres"
                } else if (password != Confirmpassword) {
                    passwordError = "Las contraseñas no coinciden"
                } else if (email.isEmpty() || nombre.isEmpty() || apellido.isEmpty()) {
                    // Validamos que tampoco dejen el apellido vacío
                    passwordError = "Por favor llena todos los campos"
                } else {
                    // NUEVO: Enviamos el nombre y apellido ingresados
                    onRegisterClick(email, password, nombre, apellido, "Arrendatario")
                }
            },
            enabled = authState !is AuthState.Loading,
            shape = RoundedCornerShape(8.dp),
            contentPadding = PaddingValues(vertical = 4.dp, horizontal = 80.dp),
            colors = ButtonDefaults.buttonColors(MainColor),
            modifier = Modifier
                .width(250.dp)
                .height(48.dp)
        ) {
            if (authState is AuthState.Loading) {
                CircularProgressIndicator(modifier = Modifier.size(24.dp), color = ButtonTextColor, strokeWidth = 2.dp)
            } else {
                Text(text = "Confirmar", color = ButtonTextColor, fontSize = 18.sp)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}
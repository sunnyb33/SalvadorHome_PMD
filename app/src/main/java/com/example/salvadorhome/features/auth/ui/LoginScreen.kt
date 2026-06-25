package com.example.salvadorhome.features.auth.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
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

// 1. LA PANTALLA INTELIGENTE (Habla con el ViewModel y Firebase. NO lleva @Preview)
@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit = {},
    onForgotPassword: () -> Unit = {},
    // Inyectamos el ViewModel usando el Factory de tu compañero
    viewModel: AuthViewModel = viewModel(factory = AuthViewModelFactory())
) {
    val authState by viewModel.authState.collectAsState()

    LaunchedEffect(authState) {
        if (authState is AuthState.Success) {
            onLoginSuccess()
            viewModel.resetState()
        }
    }

    // Llamamos a la parte visual
    LoginScreenContent(
        authState = authState,
        onForgotPassword = onForgotPassword,
        onLoginClick = { email, password -> viewModel.login(email, password) }
    )
}

// 2. EL PREVIEW (Datos falsos para no crashear)
@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    LoginScreenContent(
        authState = AuthState.Idle,
        onForgotPassword = {},
        onLoginClick = { _, _ -> }
    )
}

// 3. LA PANTALLA VISUAL (Solo dibuja el diseño que ya tenías)
@Composable
fun LoginScreenContent(
    authState: AuthState,
    onForgotPassword: () -> Unit,
    onLoginClick: (String, String) -> Unit
) {
    val TextColor = Color(0xFF0A1128)
    val MainColor = Color(0xFF0A1128)
    val ButtonTextColor = Color(0xFFF5F5F5)
    val PlaceholderColor = Color(0xFFAAB2CB)

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp, vertical = 48.dp)
            .background(Color(0xFFF8F9FA))
    ) {
        Text(
            text = "Bienvenido de nuevo",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.SansSerif,
            color = MainColor,
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email", color = TextColor) },
            placeholder = { Text("ejemplo@email.com", color = PlaceholderColor) },
            colors = TextFieldDefaults.colors(
                unfocusedTextColor = Color(0xFFA1B2E8),
                focusedIndicatorColor = Color(0xFFEBEEFA),
                focusedTextColor = TextColor
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña", color = TextColor) },
            placeholder = { Text("Ingresa tu contraseña", color = PlaceholderColor) },
            colors = TextFieldDefaults.colors(
                unfocusedTextColor = Color(0xFFB5D3E0),
                focusedIndicatorColor = Color(0xFFB5D3E0),
                focusedTextColor = TextColor
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextButton(onClick = onForgotPassword) {
            Text(
                text = "¿Has olvidado tu contraseña?",
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.SansSerif,
                color = TextColor
            )
        }

        if (authState is AuthState.Error) {
            Text(
                text = (authState as AuthState.Error).message,
                color = Color.Red,
                fontSize = 14.sp,
                modifier = Modifier.padding(top = 4.dp)
            )
        }

        Spacer(modifier = Modifier.weight(0.1f))

        Button(
            onClick = { onLoginClick(email, password) },
            enabled = authState !is AuthState.Loading,
            shape = RoundedCornerShape(8.dp),
            contentPadding = PaddingValues(vertical = 4.dp, horizontal = 80.dp),
            colors = ButtonDefaults.buttonColors(MainColor),
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            if (authState is AuthState.Loading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    color = ButtonTextColor,
                    strokeWidth = 2.dp
                )
            } else {
                Text(
                    text = "Confirmar",
                    fontSize = 18.sp,
                    color = ButtonTextColor
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))
        Spacer(modifier = Modifier.height(16.dp))
    }
}
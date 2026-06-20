package com.example.salvadorhome.features.auth.ui

import android.widget.Space
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.salvadorhome.features.auth.viewmodel.AuthViewModel

@Preview(showBackground = true) //Previsualizacion previa
@Composable
fun RegisterScreen(
    //onRegisterSuccess: () -> Unit,
    //viewModel: AuthViewModel = viewModel()
){

    //Colores:
    val TextColor = Color(0xFF0A1128)

    //val state by viewModel.uiState.collectAsState()
    var nombre by remember { mutableStateOf("") } //Temporal
    var apellido by remember { mutableStateOf("") } //Temporal
    var password by remember { mutableStateOf("") } //Temporal

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(32.dp, 48.dp)
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

        /*
        * Modificar value y value on change ya que no tienen logica
        * estan descritos de esta manera solo para mostrarse, se modificara cuando
        * la logica del viewmodel este realizada
        * value = state.nombre,
        * onValueChange = viewModel::onNombreChange,
        * */

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
            value = password,
            onValueChange = { password = it },
            label = {Text("Contraseña", fontFamily = FontFamily.SansSerif)},
            placeholder = {Text("Contraseña", fontFamily = FontFamily.SansSerif)},
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = password,
            onValueChange = {password = it},
        )




    }
}



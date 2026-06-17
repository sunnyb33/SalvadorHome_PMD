package com.example.salvadorhome.features.auth.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.salvadorhome.features.auth.viewmodel.AuthViewModel
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember

@Composable
fun LoginScreen(
    onLoginSucces: () -> Unit,
    onForgotPassword: () -> Unit,
    viewModel: AuthViewModel = viewModel()
){
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    //val state by viewModel.uiState.collectAsState()
/*
*La logica de la autenticación esta vacia, por tanto aun no puedo añadir el viewmodel
* en el codigo del fronten T T
* */
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp, vertical = 48.dp)
    ) {
        Text(
            text = "Bienvenido de nuevo",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(12.dp))


        /*
        * Modificar value y value on change ya que no tienen logica
        * estan descritos de esta manera solo para mostrarse, se modificara cuando
        * la logica del viewmodel este realizada*/
        OutlinedTextField(
            value = email,
            onValueChange = { email = it } ,
            label = { Text("Email") },
            placeholder = {Text("ejemplo@email.com")},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(8.dp))

        //Esto es opcional para no complicar el backend al momento de recuperar la contraseña
        TextButton(onClick = onForgotPassword) {
            Text("¿Has olvidado tu contraseña?")
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {/*viewModel.login(onLoginSucces)*/}, //Se añadira cuando la logica sea implementada
            //enabled = !state.isLoading,
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ){/*
            if(state.isLoading){
                CircularProgressIndicator(modifier = Modifier.size(20.dp), strokeWidth = 2.dp)
            } else {
                Text("Confirmar")
            }*/
        }

            Spacer(modifier = Modifier.height(12.dp))

        /* Boton login por google el cual no esta definido en los alcances, por tanto
        * queda opcional su adición al proyecto, pero el diseño estara realizado en caso de que
        * se termine añadiendo su diseño se encuentra aca comentado
        *
        * OutlinedBUtton(
        *   OnClick = {/Viewmodel.loginWithGoogle()/}
        *   shape = RoundedCornerShape(8.dp),
        *   modifier = Modifier
        *       .fillMaxWidth()
        *       .height(48.dp)
        * ){
        *     Row(){
        *       Image (imagen de google)
        *       Text ("Iniciar sesión con google)
        *   }
        * }
        */

            Spacer(modifier = Modifier.height(16.dp))
    }

}
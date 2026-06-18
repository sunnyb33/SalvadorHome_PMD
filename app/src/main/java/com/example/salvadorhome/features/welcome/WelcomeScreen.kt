package com.example.salvadorhome.features.welcome

import android.R.attr.fontWeight
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.salvadorhome.R

@Preview(showBackground = true) //Previsualizacion previa
@Composable
fun WelcomeScreen(
    onLoginClick: () -> Unit = {},
    onRegistrerClick: () -> Unit = {}
){
    //Colores:
    val TextColor = Color(0xFF0A1128)
    val MainColor = Color(0xFF0A1128)
    val ButtonTextColor = Color(0xFFF5F5F5)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F9FA))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(top = 24.dp)
        ) {
            Text(
                text = "Bienvenido a SalvadorHouse",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.SansSerif,
                color = TextColor,
                modifier = Modifier.padding(10.dp)
            )

            //Logo
            Image(
                painter = painterResource(id = R.drawable.logo_proyecto_pmd_photoroom),
                contentDescription = "Logo oficial de la aplicación",
                modifier = Modifier
                    .size(300.dp)
                    .padding(20.dp),
                contentScale = ContentScale.Fit
            )
        }

        Spacer(modifier = Modifier.weight(0.5f))

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.padding(bottom = 32.dp)
        ) {
            //Boton de Iniciar sesion
            Button(
                onClick = onLoginClick,
                shape = RoundedCornerShape(8.dp),
                contentPadding = PaddingValues(vertical = 4.dp, horizontal = 80.dp),
                colors = ButtonDefaults.outlinedButtonColors(MainColor),
                modifier = Modifier
                    .height(40.dp)
            ) {
                Text(
                    text = "Iniciar sesión",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    fontFamily = FontFamily.SansSerif,
                    color = ButtonTextColor
                )
            }

            //Boton de registrarse
            Button(
                onClick = onRegistrerClick,
                shape = RoundedCornerShape(8.dp),
                contentPadding = PaddingValues(vertical = 4.dp, horizontal = 80.dp),
                colors = ButtonDefaults.outlinedButtonColors(MainColor),
                modifier = Modifier
                    .height(40.dp)
            ) {
                Text(
                    text = "Registrarse",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.SansSerif,
                    modifier = Modifier
                        .padding(horizontal = 7.dp),
                    color = ButtonTextColor
                )
            }
        }
    }
}
package com.example.salvadorhome.features.auth.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.salvadorhome.R
import com.example.salvadorhome.features.auth.ui.components.RoleOption
import com.example.salvadorhome.features.auth.viewmodel.AuthViewModel

@Composable
fun RoleSelectionScreen(
    viewModel: AuthViewModel,
    onRoleConfirmed: () -> Unit = {}
) {
    val TextColor = Color(0xFF0A1128)
    val MainColor = Color(0xFF0A1128)
    val ButtonTextColor = Color(0xFFF5F5F5)

    val state by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 50.dp, start = 32.dp, end = 32.dp, bottom = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "¿Que es lo que deseas hacer en SalvadorHouse?",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Start,
            color = TextColor
        )

        Spacer(modifier = Modifier.height(50.dp))

        RoleOption(
            icon = R.drawable.icono_arrendador,
            title = "Arrendador",
            description = "Rentas y agendas estadías en un airbnb",
            isSelected = state.selectedRole == "ARRENDADOR",
            onClick = { viewModel.onRoleSelected("ARRENDADOR") }
        )

        RoleOption(
            icon = R.drawable.arrendatario_icon,
            title = "Arrendatario",
            description = "Proporcionas hospedajes en tu airbnb publicado",
            isSelected = state.selectedRole == "ARRENDATARIO",
            onClick = { viewModel.onRoleSelected("ARRENDATARIO") }
        )

        RoleOption(
            icon = R.drawable.ambos_rol_icon,
            title = "Ambos",
            description = "Proporcionas hospedajes y rentas hospedajes en el airbnb que desees",
            isSelected = state.selectedRole == "AMBOS",
            onClick = { viewModel.onRoleSelected("AMBOS") }
        )

        state.error?.let {
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = it,
                color = Color.Red,
                fontSize = 13.sp
            )
        }

        Spacer(modifier = Modifier.height(100.dp))

        Button(
            onClick = onRoleConfirmed,
            enabled = state.selectedRole != null,
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MainColor),
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Text(
                text = "Confirmar",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.SansSerif,
                color = ButtonTextColor
            )
        }
    }
}
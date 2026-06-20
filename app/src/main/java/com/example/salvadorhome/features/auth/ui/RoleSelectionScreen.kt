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

@Composable
private fun RoleOption(
    icon: Int,
    title: String,
    description: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val MainColor    = Color(0xFF0A1128)
    val CheckedColor = Color(0xFF3357CC)

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .background(Color(0xFFF8F9FA))
            .fillMaxWidth()
            .padding(vertical = 15.dp)
    ) {
        Image(
            painter = painterResource(id = icon),
            contentDescription = title,
            modifier = Modifier.size(60.dp)
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 12.dp)
        ) {
            Text(text = title, fontSize = 22.sp, fontWeight = FontWeight.SemiBold)
            Text(text = description, fontSize = 14.sp)
        }

        Checkbox(
            checked = isSelected,
            onCheckedChange = { onClick() },
            colors = CheckboxDefaults.colors(
                uncheckedColor = MainColor,
                checkedColor = CheckedColor
            ),
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RoleSelectionScreen(
    onRoleConfirmed: () -> Unit = {}
    // viewModel: AuthViewModel = viewModel()
) {
    val TextColor      = Color(0xFF0A1128)
    val MainColor      = Color(0xFF0A1128)
    val ButtonTextColor = Color(0xFFF5F5F5)

    // Se agregara cuando haya logica: viewModel.onRoleSelected(role)
    var selectedRole by remember { mutableStateOf<String?>(null) }

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
            isSelected = selectedRole == "ARRENDADOR",
            onClick = { selectedRole = "ARRENDADOR" }
        )

        RoleOption(
            icon = R.drawable.arrendatario_icon,
            title = "Arrendatario",
            description = "Proporcionas hospedajes en tu airbnb publicado",
            isSelected = selectedRole == "ARRENDATARIO",
            onClick = { selectedRole = "ARRENDATARIO" }
        )

        RoleOption(
            icon = R.drawable.ambos_rol_icon,
            title = "Ambos",
            description = "Proporcionas hospedajes y rentas hospedajes en el airbnb que desees",
            isSelected = selectedRole == "AMBOS",
            onClick = { selectedRole = "AMBOS" }
        )

        Spacer(modifier = Modifier.height(100.dp))

        Button(
            onClick = onRoleConfirmed,
            // onClick = { viewModel.confirmRole(onRoleConfirmed) },
            // enabled = selectedRole != null,
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
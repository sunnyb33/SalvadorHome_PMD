package com.example.salvadorhome.features.auth.ui

import android.R.attr.color
import android.R.attr.id
import android.R.attr.label
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxColors
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Label
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.salvadorhome.R
import com.example.salvadorhome.features.auth.viewmodel.AuthViewModel

//Los preview se removeran cuando el backend y front esten unidos
@Preview(showBackground = true) //Previsualizacion previa
@Composable
fun RoleSelectionScreen(
    onRoleConfirmed: () -> Unit = {},
    viewModel: AuthViewModel = viewModel()
){
    //Colores:
    val TextColor = Color(0xFF0A1128)
    val MainColor = Color(0xFF0A1128)
    val ButtonTextColor = Color(0xFFF5F5F5)
    val CheckedColor = Color(0xFF3357CC)

    //val state by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp,48.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "¿Que es lo que deseas hacer en SalvadorHouse?",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Start,
            color = TextColor
        )

        Column{
            //Columna de seleccion de roles
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp)
            ){
                Icon(
                    painter = painterResource(id=(R.drawable.icon_arrendador)),
                    contentDescription = "Icono Arrendador",
                    tint = Color.Unspecified,
                    modifier = Modifier
                        .size(60.dp)
                        )
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 12.dp)
                ) {
                    Text(text = "Arrendador", fontSize = 22.sp,fontWeight = FontWeight.SemiBold)
                    Text(text = "Rentas y agendas estadías en un airbnb", fontSize = 14.sp)
                }

                Checkbox(
                    checked = false,
                    onCheckedChange = {},
                    colors = CheckboxDefaults.colors(uncheckedColor = MainColor, checkedColor = CheckedColor),
                    modifier = Modifier
                        .padding(start = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            //Rol arrendatario
            Row() { }
        }
    }
}
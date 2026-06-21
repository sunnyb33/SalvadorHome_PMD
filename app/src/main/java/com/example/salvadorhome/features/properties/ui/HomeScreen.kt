package com.example.salvadorhome.features.properties.ui


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialogDefaults.containerColor
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.salvadorhome.features.properties.model.PropertyCategory
import com.example.salvadorhome.features.shared.ui.components.SalvadorBottomBar

@Preview(showBackground = true)
@Composable
fun HomeScreen(
    onPropertyClick: (String) -> Unit = {},
    onNavItemClick: (String) -> Unit = {}
    //viewmodel: HomeViewModel = viewModel()
){
    //Colores
    val MainColor       = Color(0xFF0A1128)
    val SecondaryColor = Color(0xFF519EBA)
    val ChipSelectedBg  = Color(0xFFB5D3E0)
    val ChipTextColor   = Color(0xFFF5F5F5)

    //Cambiar el estado segun el viewmodel de navegacion cuando el backend lo defina
    var SelectedCategory by remember { mutableStateOf(PropertyCategory.TODOS) }
    var currentRoute by remember { mutableStateOf(""/*(NavRoutes.HOME*/) }

/* Añadir cuando la logica este hecha T T
    val filteredProperties = remember(selectedCategory) {
        if (selectedCategory == PropertyCategory.TODOS) FakeProperties.list
        else FakeProperties.list.filter { it.category == selectedCategory }
    }*/

    // Esto es temporal T T ya que la logica de navegacion no esta hecha
    var selectedIndex by remember { mutableStateOf(0) }
    val index = null

    Scaffold(
        bottomBar = {

            SalvadorBottomBar(
                selectedIndex = selectedIndex,
                onItemSelected = { index }
            )
        },
        containerColor = Color.White
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 24.dp)
        ){
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column{
                    Text(
                        text = "Bienvenido",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = MainColor
                    )
                    Text(
                        text = "¿A dónde deseas hospedarte?",
                        fontSize = 14.sp,
                        color = SecondaryColor
                    )
                }
            }
        }

    }
}

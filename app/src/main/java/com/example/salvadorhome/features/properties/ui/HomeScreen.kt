package com.example.salvadorhome.features.home.ui

import androidx.compose.material3.Button
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.salvadorhome.data.local.database.DatabaseProvider
import com.example.salvadorhome.data.repository.PropertyRepository
import com.example.salvadorhome.features.properties.model.PropertyCategory
import com.example.salvadorhome.features.properties.viewmodel.PropertyViewModel
import com.example.salvadorhome.features.properties.viewmodel.PropertyViewModelFactory
import com.example.salvadorhome.features.shared.ui.components.PropertyCard
import com.example.salvadorhome.features.shared.ui.components.SalvadorBottomBar
@Preview
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onPropertyClick: (String) -> Unit = {},
    userRole: String = "",
    onHostClick: () -> Unit = {}
)
{
    val context = LocalContext.current

    val viewModel: PropertyViewModel = viewModel(
        factory = PropertyViewModelFactory(
            PropertyRepository(
                DatabaseProvider
                    .getDatabase(context)
                    .propertyDao()
            )
        )
    )
    val MainColor = Color(0xFF0A1128)
    val ChipSelectedBg = Color(0xFF0A1128)
    val ChipTextColor  = Color(0xFFF5F5F5)
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadProperties()
    }

    var selectedCategory by remember {
        mutableStateOf(PropertyCategory.TODOS)
    }

    val filteredProperties = remember(
        selectedCategory,
        state.properties
    ) {

        if (selectedCategory == PropertyCategory.TODOS) {
            state.properties
        } else {
            state.properties.filter {
                it.category == selectedCategory
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 24.dp)
    ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Bienvenido",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = MainColor
                    )
                    Text(
                        text = "¿A dónde deseas hospedarte?",
                        fontSize = 13.sp,
                        color = Color.Gray
                    )

                    if (userRole.equals("AMBOS", ignoreCase = true)) {
                        Spacer(modifier = Modifier.height(10.dp))

                        Button(
                            onClick = onHostClick
                        ) {
                            Text("Ir a modo anfitrión")
                        }
                    }
                }

                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFE0E0E0)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = android.R.drawable.ic_menu_myplaces),
                        contentDescription = "Perfil",
                        tint = MainColor,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xFF0A1128)),
                contentAlignment = Alignment.BottomStart
            ) {
                //Cambiar por una foto despues
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "🇸🇻 +120 destinos certificados",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = "en todo El Salvador",
                        fontSize = 13.sp,
                        color = Color.White.copy(alpha = 0.8f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier.horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                PropertyCategory.entries.forEach { category ->
                    val isSelected = selectedCategory == category
                    FilterChip(
                        selected = isSelected,
                        onClick = { selectedCategory = category },
                        label = { Text(text = category.label, fontSize = 13.sp) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = ChipSelectedBg,
                            selectedLabelColor     = ChipTextColor,
                            containerColor         = Color(0xFFF0F0F0),
                            labelColor             = MainColor
                        ),
                        border = FilterChipDefaults.filterChipBorder(
                            enabled             = true,
                            selected            = isSelected,
                            borderColor         = Color.Transparent,
                            selectedBorderColor = Color.Transparent
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Destacados",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = MainColor
            )

            Spacer(modifier = Modifier.height(12.dp))

            if (filteredProperties.isEmpty()) {
                Text(
                    text = "No hay publicaciones en esta categoría",
                    fontSize = 13.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(vertical = 20.dp)
                )
            } else {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    filteredProperties.forEach { property ->
                        PropertyCard(
                            property = property,
                            onClick = { onPropertyClick(property.id) }
                        )
                    }
                }
            }
        }
    }



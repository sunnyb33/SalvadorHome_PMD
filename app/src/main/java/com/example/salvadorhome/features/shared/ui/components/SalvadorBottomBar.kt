package com.example.salvadorhome.features.shared.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Bed
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Publish
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.salvadorhome.core.theme.SalvadorHomeTheme
import com.example.salvadorhome.core.theme.SalvadorLavender
import com.example.salvadorhome.core.theme.SalvadorNavy

data class BottomBarItem(
    val label: String,
    val icon: ImageVector
)

val SalvadorBottomBarItems = listOf(
    BottomBarItem("Inicio", Icons.Default.Home),
    BottomBarItem("Explorar", Icons.Default.Explore),
    BottomBarItem("Mensajes", Icons.AutoMirrored.Filled.Send),
    BottomBarItem("Publicar", Icons.Default.Publish),
    BottomBarItem("Reservas", Icons.Default.Bed),
    BottomBarItem("Perfil", Icons.Default.Person)
)

@Composable
fun SalvadorBottomBar(
    selectedIndex: Int,
    onItemSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        NavigationBar(
            modifier = Modifier
                .fillMaxWidth()
                .height(72.dp)
                .clip(RoundedCornerShape(36.dp)),
            containerColor = SalvadorLavender,
            tonalElevation = 0.dp
        ) {
            SalvadorBottomBarItems.forEachIndexed { index, item ->
                NavigationBarItem(
                    selected = selectedIndex == index,
                    onClick = { onItemSelected(index) },
                    icon = {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.label
                        )
                    },
                    label = {
                        Text(
                            text = item.label,
                            fontSize = 9.sp,
                            maxLines = 1
                        )
                    },
                    alwaysShowLabel = true,
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = SalvadorNavy,
                        selectedTextColor = SalvadorNavy,
                        indicatorColor = Color.White.copy(alpha = 0.55f),
                        unselectedIconColor = Color.Black,
                        unselectedTextColor = SalvadorNavy
                    )
                )
            }
        }
    }
}

@Preview(name = "Barra inferior", showBackground = true, widthDp = 390)
@Composable
private fun SalvadorBottomBarPreview() {
    SalvadorHomeTheme {
        SalvadorBottomBar(
            selectedIndex = 0,
            onItemSelected = {}
        )
    }
}

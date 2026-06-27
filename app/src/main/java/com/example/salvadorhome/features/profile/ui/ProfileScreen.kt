package com.example.salvadorhome.features.profile.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.NotificationsNone
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.salvadorhome.core.theme.SalvadorBlue
import com.example.salvadorhome.core.theme.SalvadorBlue600
import com.example.salvadorhome.core.theme.SalvadorHomeTheme
import com.example.salvadorhome.core.theme.SalvadorLavender
import com.example.salvadorhome.core.theme.SalvadorLavenderLight
import com.example.salvadorhome.core.theme.SalvadorNavy
import com.example.salvadorhome.core.theme.SalvadorOutline
import com.example.salvadorhome.features.shared.ui.components.SalvadorBottomBar
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.salvadorhome.features.profile.viewmodel.ProfileViewModel

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    onEditProfile: () -> Unit = {},
    onNotifications: () -> Unit = {},
    onSecurity: () -> Unit = {},
    onHelp: () -> Unit = {},
    onLogout: () -> Unit = {}
) {
    val viewModel: ProfileViewModel = viewModel()
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadProfile()
    }

    val profile = state.profile

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 20.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        item {
            Text("Mi perfil", color = SalvadorNavy, fontSize = 25.sp, fontWeight = FontWeight.Bold)
            Text("Administra tu información y preferencias", color = SalvadorBlue, fontSize = 13.sp)
        }
        item {
            ProfileHeader(
                fullName = "${profile?.nombre.orEmpty()} ${profile?.apellido.orEmpty()}",
                role = profile?.rol.orEmpty(),
                onEditProfile = onEditProfile
            )
        }
        item { SectionTitle("Información personal") }
        item {
            PersonalInformationCard(
                fullName = "${profile?.nombre.orEmpty()} ${profile?.apellido.orEmpty()}",
                email = profile?.email.orEmpty()
            )
        }
        item { SectionTitle("Configuración") }
        item { ProfileOptionsCard(onNotifications, onSecurity, onHelp) }
        item { LogoutButton(onLogout) }
        item { Spacer(Modifier.height(4.dp)) }
    }
}

@Composable
private fun SectionTitle(text: String) {
    Text(text, color = SalvadorNavy, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
}

@Composable
private fun ProfileHeader(
    fullName: String,
    role: String,
    onEditProfile: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(SalvadorLavenderLight, RoundedCornerShape(22.dp))
            .padding(22.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Surface(
            modifier = Modifier
                .size(96.dp)
                .clickable(onClick = onEditProfile),
            shape = CircleShape,
            color = SalvadorLavender
        ) {
            Icon(
                Icons.Default.Person,
                contentDescription = "Editar foto de perfil",
                modifier = Modifier.padding(20.dp),
                tint = SalvadorNavy
            )
        }
        Text(
            fullName.ifBlank { "Usuario" },
            modifier = Modifier.padding(top = 12.dp),
            color = SalvadorNavy,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Surface(
            modifier = Modifier.padding(top = 7.dp),
            color = SalvadorLavender,
            shape = RoundedCornerShape(50)
        ) {
            Text(
                role.ifBlank { "Sin rol" },
                modifier = Modifier.padding(horizontal = 14.dp, vertical = 5.dp),
                color = SalvadorNavy,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
private fun PersonalInformationCard(
    fullName: String,
    email: String
) {
    ProfileCard {
        InformationRow(Icons.Default.Badge, "Nombre completo", fullName.ifBlank { "Usuario" })
        HorizontalDivider(color = SalvadorOutline.copy(alpha = 0.65f))
        InformationRow(Icons.Default.Email, "Correo electrónico", email.ifBlank { "Sin correo" })
    }
}

@Composable
private fun InformationRow(icon: ImageVector, label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            modifier = Modifier.size(40.dp),
            shape = CircleShape,
            color = SalvadorLavenderLight
        ) {
            Icon(icon, null, modifier = Modifier.padding(9.dp), tint = SalvadorBlue600)
        }
        Column(modifier = Modifier.padding(start = 12.dp)) {
            Text(label, color = SalvadorBlue, fontSize = 11.sp)
            Text(value, color = SalvadorNavy, fontSize = 14.sp, fontWeight = FontWeight.Medium)
        }
    }
}

@Composable
private fun ProfileOptionsCard(
    onNotifications: () -> Unit,
    onSecurity: () -> Unit,
    onHelp: () -> Unit
) {
    ProfileCard {
        OptionRow(Icons.Default.NotificationsNone, "Notificaciones", onNotifications)
        HorizontalDivider(color = SalvadorOutline.copy(alpha = 0.65f))
        OptionRow(Icons.Default.Lock, "Privacidad y seguridad", onSecurity)
        HorizontalDivider(color = SalvadorOutline.copy(alpha = 0.65f))
        OptionRow(Icons.Default.HelpOutline, "Ayuda y soporte", onHelp)
    }
}

@Composable
private fun ProfileCard(content: @Composable () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, SalvadorOutline)
    ) {
        content()
    }
}

@Composable
private fun OptionRow(icon: ImageVector, title: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, null, tint = SalvadorBlue600)
        Text(
            title,
            modifier = Modifier
                .weight(1f)
                .padding(start = 14.dp),
            color = SalvadorNavy,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        )
        Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, null, tint = SalvadorBlue)
    }
}

@Composable
private fun LogoutButton(onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .background(Color(0xFFFFF3E0), RoundedCornerShape(16.dp))
            .padding(16.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(Icons.AutoMirrored.Filled.ExitToApp, null, tint = Color(0xFFD45A00))
        Text(
            "Cerrar sesión",
            modifier = Modifier.padding(start = 8.dp),
            color = Color(0xFFD45A00),
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Preview(name = "Perfil", showBackground = true, widthDp = 390, heightDp = 844)
@Composable
private fun ProfileScreenPreview() {
    SalvadorHomeTheme {
        Scaffold(
            bottomBar = { SalvadorBottomBar(selectedIndex = 5, onItemSelected = {}) }
        ) { padding ->
            ProfileScreen(modifier = Modifier.padding(padding))
        }
    }
}


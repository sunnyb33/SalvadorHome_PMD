package com.example.salvadorhome.features.shared.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.salvadorhome.features.properties.model.Property
import com.example.salvadorhome.features.properties.model.PropiedadesTemporales

@Preview(showBackground = true)
@Composable
fun PropertyCard(
    property: Property = PropiedadesTemporales.list[0],
    onClick: () -> Unit ={}
) {
    val MainColor = Color(0xFF0A1128)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFFF5F5F5))
            .clickable { onClick() }
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Cambiar la imagen por una url cuando el backend este listo
        Image(
            painter = painterResource(id = property.imageRes),
            contentDescription = property.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(width = 110.dp, height = 90.dp)
                .clip(RoundedCornerShape(8.dp))
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(text = property.title, fontWeight = FontWeight.SemiBold, fontSize = 14.sp, color = MainColor)
            Text(text = property.location, fontSize = 12.sp, color = Color(0xFF0089AB))
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "$${property.pricePerNight}/noche · ${property.capacity} huéspedes",
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = MainColor
            )
        }

        IconButton(onClick = { /* TODO: viewModel.toggleFavorite(property.id) */ }) {
            Icon(
                painter = painterResource(id = android.R.drawable.btn_star_big_off),
                contentDescription = "Favorito",
                tint = MainColor,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}
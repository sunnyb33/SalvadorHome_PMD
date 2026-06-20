package com.example.salvadorhome.features.properties.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
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
    onClick: () -> Unit = {}
){
    //Colores
    val TextColor = Color(0xFF3357CC)
    val MainColor = Color(0xFF0A1128)
    val ButtonTextColor = Color(0xFFF5F5F5)
    val PlaceholderColor = Color(0xFFAAB2CB)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFFF8F9FA))
            .clickable {onClick ()}
            .padding(8.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ){
        //Las imagenes se reemplazaran con AsyncImage cuando el backend este listo:
        Image(
            painter = painterResource(id = property.imageRes),
            contentDescription = property.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(width = 110.dp, height = 90.dp)
                .clip(RoundedCornerShape(8.dp))
        )

        Spacer(modifier = Modifier.width(50.dp))

        //Inofrmacion de la publicacion
        Column(modifier = Modifier.width(100.dp)) {
            Text(
                text = property.location,
                fontSize = 12.sp,
                color = MainColor
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "$${property.pricePerNight}/noche · ${property.capacity} huéspedes",
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = TextColor
            )
        }

        //Boton defavoritos
        IconButton(
            onClick = {/*Logica para marcar una publicacion como favorita uwu*/},
            ) {
            Icon(
                painter = painterResource(id = android.R.drawable.btn_star_big_off),
                contentDescription = "Favorito",
                tint = MainColor,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

//Pantalla de bienvenida para arrendador:

@Preview(showBackground = true)
@Composable
fun HomeScreen(
    onPropertyClick: (String) -> Unit = {},
    onNavItemClick: (String) -> Unit = {}
    //viewmodel: HomeViewModel = viewModel()
){

}

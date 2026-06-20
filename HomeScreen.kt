package com.example.salvadorhome.features.properties.model.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.salvadorhome.features.properties.model.PropiedadesTemporales

@Preview(showBackground = true)//Temporal - cuadno el backend se añada se eliminara
@Composable
fun PropertyCard(
    property: PropiedadesTemporales.list,
    onClick:() -> Unit = {}
){
    //Colores
    val TextColor = Color(0xFF0A1128)
    val MainColor = Color(0xFF0A1128)
    val ButtonTextColor = Color(0xFFF5F5F5)

    Row(
        modifier = Modifier
            .background(Color(0xFFF8F9FA))
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .clickable{ onClick() }
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        /*Aca se ubicaria una imagen de la publicacion hecha, la cual
        * se necesita la URL real del backend*/
        Image(
            painter = painterResource(id = property.imageRes),
            contentDescription = property.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(width = 110.dp, height = 90.dp)
                .clip(RoundedCornerShape(8.dp))
        )

        Spacer(modifier = Modifier.height(12.dp))

        Column(

        ){}
    }
}
package com.example.salvadorhome.features.properties.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.salvadorhome.features.properties.model.Property
import com.example.salvadorhome.features.properties.model.PropiedadesTemporales

@Preview(showBackground = true)
@Composable
fun PropertyCard(
    property: Property = PropiedadesTemporales.list[0],
    onClick: () -> Unit = {}
){
    //Colores

}
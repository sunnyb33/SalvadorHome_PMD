package com.example.salvadorhome.features.properties.model
import android.R

data class Property(
    val id: String,
    val title: String,
    val location: String,
    val pricePerNight: Double,
    val capacity: Int,
    val category: PropertyCategory,
    val imageRes: Int, //Se cambiara a string cuando el backend este listo por las url
    val isFavorite: Boolean = false
)

enum class PropertyCategory(val label: String){
    TODOS("Todos"),
    PLAYA("Playa"),
    NATURALEZA("Naturaleza"),
    HOTEL("Hotel"),
    MONTAÑA("Montaña")
}

//Cuando el backend este listo esto se puede eliminar uwu
object PropiedadesTemporales{
    val list = listOf(
        Property(
            id = "1",
            title = "Cabaña Frente al mar",
            location = "La libertad, El Salvador",
            pricePerNight = 50.0,
            capacity = 4,
            category = PropertyCategory.PLAYA,
            imageRes = R.drawable.ic_menu_gallery
        ),

        Property(
            id = "2",
            title = "Finca en las montañas",
            location = "Santa Ana, El Salvador",
            pricePerNight = 30.0,
            capacity = 6,
            category = PropertyCategory.NATURALEZA,
            imageRes = R.drawable.ic_menu_gallery
        ),

        Property(
            id = "3",
            title = "Hotel boutique centro",
            location = "San Salvador, El Salvador",
            pricePerNight = 60.0,
            capacity = 2,
            category = PropertyCategory.HOTEL,
            imageRes = R.drawable.ic_menu_gallery
        ),

        Property(
            id = "4",
            title = "Casa de playa familiar",
            location = "El Tunco, El Salvador",
            pricePerNight = 55.0,
            capacity = 8,
            category = PropertyCategory.PLAYA,
            imageRes = R.drawable.ic_menu_gallery
        )
    )
}

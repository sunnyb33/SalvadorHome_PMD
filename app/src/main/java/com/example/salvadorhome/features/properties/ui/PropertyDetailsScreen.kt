package com.example.salvadorhome.features.properties.ui

import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.salvadorhome.features.shared.ui.components.HostingArtwork
import com.example.salvadorhome.features.shared.ui.components.ServicesGrid

//Cambiar cuando el backend exista
data class PropertyDetail(
    val id: String,
    val name: String,
    val isCertified: Boolean,
    val rating: Float,
    val ownerName: String,
    val ownerRating: String,
    val pricePerNight: Double,
    val priceDescription: String,
    val description: String,
    val highlights: List<String>,
    val reviews: List<ReviewItem>,
    val bannerColors: List<Color> = listOf(Color(0xFFD7B28C), Color(0xFF78543C))
)

data class ReviewItem(
    val reviewerName: String,
    val date: String,
    val comment: String,
    val rating: Float
)

//Datos de prueba
private val fakeDetail = PropertyDetail(
    id = "1",
    name = "Cabaña frente al mar",
    isCertified = true,
    rating = 5.0f,
    ownerName = "Nombre dueño",
    ownerRating = "Calificación de arrendatario",
    pricePerNight = 45.0,
    priceDescription = "Descripción de precios: tarifa base + impuestos incluidos. Sin cargos ocultos.",
    description = "Hermosa cabaña ubicada frente al mar en La Libertad. Perfecta para familias y grupos de amigos que buscan descanso y naturaleza.",
    highlights = listOf(
        "Vista directa al océano",
        "Acceso privado a la playa",
        "Estacionamiento para 2 vehículos"
    ),
    reviews = listOf(
        ReviewItem("Reviewer name", "Enero 2026", "Excelente lugar, muy limpio y cómodo. El dueño muy atento en todo momento.", 5.0f),
        ReviewItem("Reviewer name", "Febrero 2026", "Nos encantó la vista al mar. Definitivamente volvemos.", 4.0f),
        ReviewItem("Reviewer name", "Marzo 2026", "Muy buen lugar para descansar. La cabaña tiene todo lo necesario.", 5.0f)
    )
)

@Composable
private fun StarRating(rating: Float, modifier: Modifier = Modifier) {
    Row(modifier = modifier) {
        repeat(5) { index ->
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = null,
                tint = if (index < rating) Color(0xFFFFB800) else Color(0xFFDDDDDD),
                modifier = Modifier.size(16.dp)
            )
        }
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = rating.toString(), fontSize = 13.sp, color = Color.Gray)
    }
}

@Composable
private fun ReviewCard(review: ReviewItem) {
    val MainColor = Color(0xFF0A1128)

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            // Avatar del reviewer
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFE0E0E0)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    tint = MainColor,
                    modifier = Modifier.size(20.dp)
                )
            }

            Spacer(modifier = Modifier.width(10.dp))

            Column {
                Text(text = review.reviewerName, fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                Text(text = review.date, fontSize = 11.sp, color = Color.Gray)
            }

            Spacer(modifier = Modifier.weight(1f))

            StarRating(rating = review.rating)
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = review.comment,
            fontSize = 12.sp,
            color = Color.DarkGray,
            lineHeight = 18.sp
        )
    }
}


@Preview(showBackground = true)
@Composable
fun PropertyDetailScreen(
    property: PropertyDetail = fakeDetail,
    onBackClick: () -> Unit = {},
    onReserveClick: () -> Unit = {}
    // viewModel: PropertyDetailViewModel = viewModel()
) {
    val MainColor    = Color(0xFF0A1128)
    val LavenderColor = Color(0xFFE8E8F8)

    var isFavorite by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {


        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(bottom = 80.dp)
        ) {


            Box(modifier = Modifier.fillMaxWidth().height(220.dp)) {
                HostingArtwork(
                    colors = property.bannerColors,
                    modifier = Modifier.fillMaxSize()
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver",
                            tint = Color.White
                        )
                    }
                    Row {
                        IconButton(onClick = { isFavorite = !isFavorite }) {
                            Icon(
                                imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                contentDescription = "Favorito",
                                tint = if (isFavorite) Color.Red else Color.White
                            )
                        }
                        IconButton(onClick = { /* TODO: compartir */ }) {
                            Icon(
                                imageVector = Icons.Default.Share,
                                contentDescription = "Compartir",
                                tint = Color.White
                            )
                        }
                    }
                }
            }

            Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp)) {

                Text(text = property.name, fontSize = 22.sp, fontWeight = FontWeight.Bold, color = MainColor)

                Spacer(modifier = Modifier.height(8.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    if (property.isCertified) {
                        Surface(
                            shape = RoundedCornerShape(50),
                            color = LavenderColor
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.CheckCircle,
                                    contentDescription = null,
                                    tint = MainColor,
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Certificado", fontSize = 12.sp, color = MainColor)
                            }
                        }
                    }
                    Surface(
                        shape = RoundedCornerShape(50),
                        color = LavenderColor
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = null,
                                tint = Color(0xFFFFB800),
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Calificación: ${property.rating}", fontSize = 12.sp, color = MainColor)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                Divider(color = Color(0xFFEEEEEE))
                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFE0E0E0)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = null,
                            tint = MainColor,
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = property.ownerName, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                        Text(text = property.ownerRating, fontSize = 12.sp, color = Color.Gray)
                    }

                    Surface(
                        shape = RoundedCornerShape(50),
                        color = MainColor
                    ) {
                        Text(
                            text = "Mensaje",
                            fontSize = 12.sp,
                            color = Color.White,
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    color = LavenderColor
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Text(text = "Descripción de precios:", fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(text = property.priceDescription, fontSize = 12.sp, color = Color.DarkGray, lineHeight = 18.sp)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(text = "Descripción del lugar", fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                Spacer(modifier = Modifier.height(6.dp))
                Text(text = property.description, fontSize = 13.sp, color = Color.DarkGray, lineHeight = 20.sp)

                Spacer(modifier = Modifier.height(16.dp))
                Divider(color = Color(0xFFEEEEEE))
                Spacer(modifier = Modifier.height(16.dp))

                Text(text = "Servicios incluidos", fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                Spacer(modifier = Modifier.height(10.dp))
                ServicesGrid() // componente del compañero

                Spacer(modifier = Modifier.height(16.dp))
                Divider(color = Color(0xFFEEEEEE))
                Spacer(modifier = Modifier.height(16.dp))

                Text(text = "Destacados", fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    property.highlights.forEach { highlight ->
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = null,
                                tint = Color(0xFF3357CC),
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = highlight, fontSize = 13.sp)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                Divider(color = Color(0xFFEEEEEE))
                Spacer(modifier = Modifier.height(16.dp))

                Text(text = "Reseñas verificadas", fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                Spacer(modifier = Modifier.height(4.dp))
                StarRating(rating = property.rating)
                Spacer(modifier = Modifier.height(12.dp))

                Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    property.reviews.forEach { review ->
                        ReviewCard(review = review)
                        Divider(color = Color(0xFFEEEEEE))
                    }
                }
            }
        }

        Surface(
            modifier = Modifier.align(Alignment.BottomCenter).fillMaxWidth(),
            shadowElevation = 8.dp,
            color = Color.White
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "$${property.pricePerNight}",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = MainColor
                    )
                    Text(text = "/ Noche", fontSize = 11.sp, color = Color.Gray)
                }

                Button(
                    onClick = onReserveClick,
                    //Cambiar cuando la logica de navegación este lista
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MainColor),
                    modifier = Modifier.height(44.dp)
                ) {
                    Text("Reservar", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = Color.White)
                }
            }
        }
    }
}
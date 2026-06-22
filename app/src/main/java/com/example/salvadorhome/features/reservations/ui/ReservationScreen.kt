package com.example.salvadorhome.features.reservations.ui

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class ReservationProperty(
    val imageRes: Int,
    val name: String,
    val location: String,
    val pricePerNight: Double,
    val maxGuests: Int,
    val cleaningFee: Double = 0.0,
    val serviceFee: Double = 0.0
)
@Composable
private fun StepProgressBar(
    currentStep: Int,
    totalSteps: Int = 3,
    modifier: Modifier = Modifier
) {
    val MainColor    = Color(0xFF0A1128)
    val LavenderColor = Color(0xFFE8E8F8)

    val progress by animateFloatAsState(
        targetValue = currentStep.toFloat() / totalSteps.toFloat(),
        label = "progress"
    )

    Column(modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            repeat(totalSteps) { index ->
                val stepNum   = index + 1
                val isDone    = stepNum < currentStep
                val isCurrent = stepNum == currentStep

                Box(
                    modifier = Modifier
                        .size(28.dp)
                        .clip(CircleShape)
                        .background(
                            when {
                                isDone    -> MainColor
                                isCurrent -> LavenderColor
                                else      -> Color(0xFFE0E0E0)
                            }
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    if (isDone) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(16.dp)
                        )
                    } else {
                        Text(
                            text = stepNum.toString(),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (isCurrent) MainColor else Color.Gray
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier
                .fillMaxWidth()
                .height(4.dp)
                .clip(RoundedCornerShape(50)),
            color = MainColor,
            trackColor = Color(0xFFE0E0E0),
            strokeCap = StrokeCap.Round
        )
    }
}

@Composable
private fun PropertySummaryCard(property: ReservationProperty) {
    val MainColor     = Color(0xFF0A1128)
    val LavenderColor = Color(0xFFE8E8F8)

    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        color = Color.White,
        shadowElevation = 2.dp
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // TODO: reemplazar por AsyncImage cuando haya URLs reales
            Image(
                painter = painterResource(id = property.imageRes),
                contentDescription = property.name,
                modifier = Modifier
                    .size(72.dp)
                    .clip(RoundedCornerShape(8.dp))
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column {
                Text(text = "Ubicación", fontSize = 11.sp, color = Color(0xFF3357CC))
                Text(text = property.name, fontWeight = FontWeight.SemiBold, fontSize = 14.sp, color = MainColor)
                Spacer(modifier = Modifier.height(4.dp))
                Surface(shape = RoundedCornerShape(50), color = LavenderColor) {
                    Text(
                        text = "✓ Verificado · sin cargos ocultos",
                        fontSize = 10.sp,
                        color = MainColor,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ReservationScreen(
    property: ReservationProperty = ReservationProperty(
        imageRes    = android.R.drawable.ic_menu_gallery,
        name        = "Nombre del local",
        location    = "La Libertad, El Salvador",
        pricePerNight = 45.0,
        maxGuests   = 5,
        cleaningFee = 0.0,
        serviceFee  = 0.0
    ),
    onBackClick: () -> Unit = {},
    onReservationConfirmed: () -> Unit = {}
    // viewModel: ReservationViewModel = viewModel()
) {
    val MainColor     = Color(0xFF0A1128)
    val LavenderColor = Color(0xFFE8E8F8)

    //cambiar cuando la logica este
    var checkIn     by remember { mutableStateOf("") }
    var checkOut    by remember { mutableStateOf("") }
    var guestCount  by remember { mutableIntStateOf(0) }


    val currentStep by remember {
        derivedStateOf {
            when {
                checkIn.isBlank() || checkOut.isBlank() -> 1
                guestCount == 0                         -> 2
                else                                    -> 3
            }
        }
    }

 //datos quemados de muestra
    val nights = 2
    val subtotal   = nights * property.pricePerNight
    val total      = subtotal + property.cleaningFee + property.serviceFee

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Volver",
                        tint = MainColor
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Confirmar reserva",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MainColor
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            StepProgressBar(currentStep = currentStep)
        }

        Divider(color = Color(0xFFEEEEEE))

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {

            PropertySummaryCard(property = property)

            Column {
                Text(
                    text = "Selecciona tus fechas",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = MainColor
                )
                Spacer(modifier = Modifier.height(12.dp))

                Text(text = "Check-In", fontSize = 13.sp, color = Color.Gray)
                Spacer(modifier = Modifier.height(6.dp))
                OutlinedTextField(
                    value = checkIn,
                    onValueChange = { checkIn = it },
                    placeholder = { Text("mm/dd/yyyy") },
                    //Cuando la logica este cambiarlo uwu
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    shape = RoundedCornerShape(8.dp)
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(text = "Check-Out", fontSize = 13.sp, color = Color.Gray)
                Spacer(modifier = Modifier.height(6.dp))
                OutlinedTextField(
                    value = checkOut,
                    onValueChange = { checkOut = it },
                    placeholder = { Text("mm/dd/yyyy") },
                    // Cambiar por DatePickerDialog cuando se integre
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    shape = RoundedCornerShape(8.dp)
                )
            }

            Column {
                Text(
                    text = "Cantidad de huéspedes",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = MainColor
                )
                Spacer(modifier = Modifier.height(12.dp))

                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    color = LavenderColor
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(text = "Huéspedes", fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                            Text(
                                text = "Mínimo: 1 huésped\nMáximo: ${property.maxGuests} huéspedes",
                                fontSize = 11.sp,
                                color = Color.Gray,
                                lineHeight = 16.sp
                            )
                        }

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            IconButton(
                                onClick = { if (guestCount > 0) guestCount-- },
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape)
                                    .background(MainColor)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Remove,
                                    contentDescription = "Restar",
                                    tint = Color.White,
                                    modifier = Modifier.size(18.dp)
                                )
                            }

                            Text(
                                text = guestCount.toString(),
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = MainColor,
                                modifier = Modifier.padding(horizontal = 16.dp)
                            )

                            IconButton(
                                onClick = { if (guestCount < property.maxGuests) guestCount++ },
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape)
                                    .background(MainColor)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = "Sumar",
                                    tint = Color.White,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                    }
                }
            }
            if (currentStep == 3) {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    color = LavenderColor
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Resumen de la reserva",
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 14.sp,
                            color = MainColor
                        )
                        Spacer(modifier = Modifier.height(12.dp))

                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text(text = "$nights noches x $${property.pricePerNight}", fontSize = 13.sp, color = Color(0xFF3357CC))
                            Text(text = "$$subtotal", fontSize = 13.sp, color = Color(0xFF3357CC))
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text(text = "Limpieza", fontSize = 13.sp, color = Color(0xFF3357CC))
                            Text(text = if (property.cleaningFee == 0.0) "Incluida" else "$${property.cleaningFee}", fontSize = 13.sp, color = Color(0xFF3357CC))
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text(text = "Cargo por servicio", fontSize = 13.sp, color = Color(0xFF3357CC))
                            Text(text = "$${property.serviceFee}", fontSize = 13.sp, color = Color(0xFF3357CC))
                        }

                        Spacer(modifier = Modifier.height(10.dp))
                        Divider(color = Color(0xFFCCCCDD))
                        Spacer(modifier = Modifier.height(10.dp))

                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text(text = "Total:", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = MainColor)
                            Text(text = "$$total", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = MainColor)
                        }
                    }
                }
            }
        }

        Surface(
            modifier = Modifier.fillMaxWidth(),
            shadowElevation = 8.dp,
            color = Color.White
        ) {
            Button(
                onClick = onReservationConfirmed,
                enabled = currentStep == 3,
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor         = MainColor,
                    disabledContainerColor = Color(0xFFCCCCCC)
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 12.dp)
                    .height(48.dp)
            ) {
                Text(
                    text = if (currentStep < 3) "Continuar" else "Confirmar reserva",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }
}
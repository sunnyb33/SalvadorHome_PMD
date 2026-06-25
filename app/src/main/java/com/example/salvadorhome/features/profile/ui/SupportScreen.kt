package com.example.salvadorhome.features.profile.ui

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private data class FaqItem(
    val question: String,
    val answer: String
)

private val faqItems = listOf(
    FaqItem(
        question = "¿Cómo realizo una reserva?",
        answer = "Buscá la propiedad que te interese, seleccioná las fechas de check-in y check-out, elegí la cantidad de huéspedes y confirmá la reserva. Recibirás una notificación con la confirmación."
    ),
    FaqItem(
        question = "¿Cómo cancelo una reserva?",
        answer = "Andá a 'Reservas realizadas' en tu perfil, seleccioná la reserva que querés cancelar y presioná 'Cancelar reserva'. Recordá que la política de cancelación varía según el arrendatario."
    ),
    FaqItem(
        question = "¿Cómo me contacto con el dueño del lugar?",
        answer = "Desde la pantalla de detalle de la publicación podés presionar el botón 'Mensaje' para comunicarte directamente con el arrendatario."
    ),
    FaqItem(
        question = "¿Mis datos están seguros?",
        answer = "Sí. SalvadorHouse protege tu información personal con encriptación y nunca comparte tus datos con terceros sin tu consentimiento."
    ),
    FaqItem(
        question = "¿Cómo cambio mi contraseña?",
        answer = "Andá a tu perfil → Privacidad y Seguridad → Cambiar contraseña. Necesitarás ingresar tu contraseña actual y la nueva."
    ),
    FaqItem(
        question = "¿Qué hago si tengo un problema con mi hospedaje?",
        answer = "Contactá primero al arrendatario por el chat interno. Si no se resuelve, escribinos a soporte@salvadorhouse.com y te responderemos en menos de 24 horas."
    )
)
@Composable
private fun FaqCard(faq: FaqItem) {
    val MainColor = Color(0xFF0A1128)
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { expanded = !expanded }
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = faq.question,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                color = MainColor,
                modifier = Modifier.weight(1f)
            )
            Icon(
                imageVector = if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                contentDescription = null,
                tint = MainColor,
                modifier = Modifier.size(20.dp)
            )
        }

        AnimatedVisibility(
            visible = expanded,
            enter = expandVertically(),
            exit = shrinkVertically()
        ) {
            Column {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = faq.answer,
                    fontSize = 13.sp,
                    color = Color.DarkGray,
                    lineHeight = 20.sp
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HelpSupportScreen(
    onBackClick: () -> Unit = {}
) {
    val MainColor     = Color(0xFF0A1128)
    val LavenderColor = Color(0xFFE8E8F8)
    val context       = LocalContext.current

    Column(modifier = Modifier.fillMaxSize()) {

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(0.dp),
            colors = CardDefaults.cardColors(containerColor = LavenderColor),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Volver",
                        tint = MainColor
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Ayuda y Soporte",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MainColor
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Text(
                text = "Preguntas frecuentes",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = MainColor
            )

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(vertical = 4.dp)) {
                    faqItems.forEachIndexed { index, faq ->
                        FaqCard(faq = faq)
                        if (index < faqItems.lastIndex) {
                            Divider(
                                color = Color(0xFFEEEEEE),
                                modifier = Modifier.padding(horizontal = 16.dp)
                            )
                        }
                    }
                }
            }

            Text(
                text = "¿No encontraste lo que buscabas?",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = MainColor
            )

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = LavenderColor),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Nuestro equipo de soporte está disponible de lunes a viernes de 8:00 AM a 5:00 PM.",
                        fontSize = 13.sp,
                        color = Color.DarkGray,
                        lineHeight = 18.sp
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Button(
                        onClick = {
                            // Es un correo ficticio a soporte
                            val intent = Intent(Intent.ACTION_SENDTO).apply {
                                data = Uri.parse("mailto:soporte@salvadorhouse.com")
                                putExtra(Intent.EXTRA_SUBJECT, "Soporte SalvadorHouse")
                            }
                            context.startActivity(intent)
                        },
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = MainColor),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                    ) {
                        Text(
                            text = "✉ Contactar soporte",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}
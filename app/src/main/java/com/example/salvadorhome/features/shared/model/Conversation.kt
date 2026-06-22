package com.example.salvadorhome.features.shared.model

import androidx.compose.ui.graphics.Color

data class Conversation(
    val id: Int,
    val contactName: String,
    val lastMessage: String,
    val time: String,
    val initials: String,
    val avatarColor: Color,
    val unread: Boolean = false
)

data class ChatMessage(
    val id: Int,
    val text: String,
    val isMine: Boolean
)

val SampleConversations = listOf(
    Conversation(1, "Casa Mar Azul", "Tu reserva está confirmada", "9:40 AM", "CM", Color(0xFF5875D5), true),
    Conversation(2, "Stephen Yustiono", "¿A qué hora sería tu llegada?", "9:36 AM", "SY", Color(0xFF43D001), true),
    Conversation(3, "Erin Steed", "Muchas gracias por la información", "9:28 AM", "ES", Color(0xFFFFAF2E)),
    Conversation(4, "Daisy Tinsley", "Nos vemos pronto", "9:20 AM", "DT", Color(0xFF0089AB)),
    Conversation(5, "Zach Friedman", "Perfecto, quedamos pendientes", "9:00 AM", "ZF", Color(0xFFA1B2E8)),
    Conversation(6, "Kyle & Aaron", "Eso es lo que estaba buscando", "8:58 AM", "KA", Color(0xFFFFC05C))
)

val SampleChatMessages = listOf(
    ChatMessage(1, "¡Hola! Me interesa conocer más sobre el hospedaje.", true),
    ChatMessage(2, "¡Claro! Con gusto te ayudo. ¿Qué deseas saber?", false),
    ChatMessage(3, "¿El alojamiento incluye estacionamiento y wifi?", true),
    ChatMessage(4, "Sí, ambos servicios están incluidos sin costo adicional.", false)
)

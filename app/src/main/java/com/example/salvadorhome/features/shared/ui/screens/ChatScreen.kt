package com.example.salvadorhome.features.shared.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.AddPhotoAlternate
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.salvadorhome.core.theme.SalvadorBlue600
import com.example.salvadorhome.core.theme.SalvadorHomeTheme
import com.example.salvadorhome.core.theme.SalvadorLavenderLight
import com.example.salvadorhome.core.theme.SalvadorNavy
import com.example.salvadorhome.core.theme.SalvadorOutline
import com.example.salvadorhome.features.shared.model.ChatMessage
import com.example.salvadorhome.features.shared.model.Conversation
import com.example.salvadorhome.features.shared.model.SampleChatMessages
import com.example.salvadorhome.features.shared.model.SampleConversations
import com.example.salvadorhome.features.shared.ui.components.SalvadorBottomBar

@Composable
fun ChatScreen(
    conversation: Conversation,
    modifier: Modifier = Modifier,
    onBack: () -> Unit
) {
    val messages = remember {
        mutableStateListOf<ChatMessage>().apply { addAll(SampleChatMessages) }
    }
    var messageText by remember { mutableStateOf("") }
    val listState = rememberLazyListState()

    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) listState.animateScrollToItem(messages.lastIndex)
    }

    Column(modifier = modifier.fillMaxSize().imePadding()) {
        ChatHeader(conversation, onBack)
        LazyColumn(
            modifier = Modifier.weight(1f).fillMaxWidth(),
            state = listState,
            contentPadding = PaddingValues(horizontal = 14.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(9.dp)
        ) {
            items(messages, key = { it.id }) { MessageBubble(it) }
        }
        MessageComposer(
            value = messageText,
            onValueChange = { messageText = it },
            onSend = {
                val text = messageText.trim()
                if (text.isNotEmpty()) {
                    messages.add(
                        ChatMessage(
                            id = (messages.maxOfOrNull { it.id } ?: 0) + 1,
                            text = text,
                            isMine = true
                        )
                    )
                    messageText = ""
                }
            }
        )
    }
}

@Composable
private fun ChatHeader(conversation: Conversation, onBack: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(SalvadorLavenderLight)
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = onBack,
            modifier = Modifier.size(42.dp).background(SalvadorBlue600, CircleShape)
        ) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, "Volver", tint = Color.White)
        }
        Box(
            modifier = Modifier
                .padding(start = 14.dp)
                .size(42.dp)
                .background(conversation.avatarColor.copy(alpha = 0.3f), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                conversation.initials,
                color = SalvadorNavy,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp
            )
        }
        Column(modifier = Modifier.padding(start = 12.dp)) {
            Text(
                conversation.contactName,
                color = SalvadorNavy,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold
            )
            Text("En línea", color = Color(0xFF2D8C01), fontSize = 11.sp)
        }
    }
}

@Composable
private fun MessageBubble(message: ChatMessage) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (message.isMine) Arrangement.End else Arrangement.Start
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.76f)
                .background(
                    color = if (message.isMine) SalvadorBlue600 else Color(0xFF262629),
                    shape = RoundedCornerShape(
                        topStart = 16.dp,
                        topEnd = 16.dp,
                        bottomStart = if (message.isMine) 16.dp else 4.dp,
                        bottomEnd = if (message.isMine) 4.dp else 16.dp
                    )
                )
                .padding(horizontal = 14.dp, vertical = 11.dp)
        ) {
            Text(message.text, color = Color.White, fontSize = 14.sp, lineHeight = 19.sp)
        }
    }
}

@Composable
private fun MessageComposer(
    value: String,
    onValueChange: (String) -> Unit,
    onSend: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(horizontal = 12.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = {}) {
            Icon(Icons.Default.AddPhotoAlternate, "Agregar imagen", tint = Color(0xFF7A7D86))
        }
        Row(
            modifier = Modifier
                .weight(1f)
                .background(Color(0xFFF7F7F9), RoundedCornerShape(24.dp))
                .padding(start = 14.dp, end = 5.dp, top = 5.dp, bottom = 5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                modifier = Modifier.weight(1f),
                singleLine = true,
                textStyle = TextStyle(color = SalvadorNavy, fontSize = 14.sp),
                decorationBox = { innerTextField ->
                    if (value.isEmpty()) {
                        Text("Escribe un mensaje", color = Color(0xFFA3A5AD), fontSize = 14.sp)
                    }
                    innerTextField()
                }
            )
            Spacer(Modifier.width(6.dp))
            IconButton(
                onClick = onSend,
                modifier = Modifier
                    .size(34.dp)
                    .background(if (value.isBlank()) SalvadorOutline else SalvadorBlue600, CircleShape)
            ) {
                Icon(
                    Icons.AutoMirrored.Filled.Send,
                    "Enviar",
                    tint = Color.White,
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}

@Preview(name = "Chat", showBackground = true, widthDp = 390, heightDp = 844)
@Composable
private fun ChatScreenPreview() {
    SalvadorHomeTheme {
        Scaffold(bottomBar = { SalvadorBottomBar(2, {}) }) { padding ->
            ChatScreen(
                conversation = SampleConversations[4],
                modifier = Modifier.padding(padding),
                onBack = {}
            )
        }
    }
}

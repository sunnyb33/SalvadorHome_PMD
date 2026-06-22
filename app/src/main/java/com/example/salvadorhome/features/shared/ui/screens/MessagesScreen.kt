package com.example.salvadorhome.features.shared.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.salvadorhome.core.theme.SalvadorBlue600
import com.example.salvadorhome.core.theme.SalvadorHomeTheme
import com.example.salvadorhome.core.theme.SalvadorLavenderLight
import com.example.salvadorhome.core.theme.SalvadorNavy
import com.example.salvadorhome.core.theme.SalvadorOutline
import com.example.salvadorhome.features.shared.model.Conversation
import com.example.salvadorhome.features.shared.model.SampleConversations
import com.example.salvadorhome.features.shared.ui.components.SalvadorBottomBar

@Composable
fun MessagesScreen(
    conversations: List<Conversation>,
    modifier: Modifier = Modifier,
    onBack: () -> Unit,
    onConversationClick: (Conversation) -> Unit
) {
    Column(modifier = modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(SalvadorLavenderLight)
                .padding(horizontal = 16.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onBack,
                modifier = Modifier
                    .size(42.dp)
                    .background(SalvadorBlue600, CircleShape)
            ) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, "Volver", tint = Color.White)
            }
            Text(
                "Mensajes",
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 42.dp),
                color = SalvadorNavy,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
        }
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            items(conversations, key = { it.id }) { conversation ->
                ConversationRow(conversation) { onConversationClick(conversation) }
            }
        }
    }
}

@Composable
private fun ConversationRow(conversation: Conversation, onClick: () -> Unit) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onClick)
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(contentAlignment = Alignment.CenterStart) {
                Box(
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .size(44.dp)
                        .background(conversation.avatarColor.copy(alpha = 0.25f), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        conversation.initials,
                        color = SalvadorNavy,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                if (conversation.unread) {
                    Box(
                        Modifier
                            .size(9.dp)
                            .background(Color(0xFF168AF5), CircleShape)
                    )
                }
            }
            Spacer(Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    conversation.contactName,
                    color = SalvadorNavy,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1
                )
                Text(
                    conversation.lastMessage,
                    color = if (conversation.unread) SalvadorBlue600 else Color(0xFF72778A),
                    fontSize = 12.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(conversation.time, color = SalvadorBlue600, fontSize = 12.sp)
                Icon(
                    Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = null,
                    tint = SalvadorOutline
                )
            }
        }
        HorizontalDivider(
            modifier = Modifier.padding(start = 80.dp),
            color = SalvadorOutline.copy(alpha = 0.7f)
        )
    }
}

@Preview(name = "Lista de mensajes", showBackground = true, widthDp = 390, heightDp = 844)
@Composable
private fun MessagesScreenPreview() {
    SalvadorHomeTheme {
        Scaffold(bottomBar = { SalvadorBottomBar(2, {}) }) { padding ->
            MessagesScreen(
                conversations = SampleConversations,
                modifier = Modifier.padding(padding),
                onBack = {},
                onConversationClick = {}
            )
        }
    }
}

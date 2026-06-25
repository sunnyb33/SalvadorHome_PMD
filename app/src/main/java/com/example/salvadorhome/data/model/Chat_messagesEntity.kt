package com.example.salvadorhome.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "chat_messages")
data class ChatMessageEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val conversationId: Int,
    val text: String,
    val isMine: Boolean,
    val time: String
)
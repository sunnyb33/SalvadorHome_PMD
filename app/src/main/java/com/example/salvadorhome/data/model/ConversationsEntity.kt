package com.example.salvadorhome.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "conversations")
data class ConversationEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val contactName: String,
    val lastMessage: String,
    val time: String,
    val initials: String,
    val avatarColor: Long,
    val unread: Boolean = false
)

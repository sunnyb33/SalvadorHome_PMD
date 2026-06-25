package com.example.salvadorhome.data.local.dao

import androidx.room.*
import com.example.salvadorhome.data.model.ChatMessageEntity

@Dao
interface ChatMessageDao {

    @Insert
    suspend fun insertMessage(
        message: ChatMessageEntity
    )

    @Query("""
        SELECT * FROM chat_messages
        WHERE conversationId = :conversationId
    """)
    suspend fun getMessagesByConversation(
        conversationId: Int
    ): List<ChatMessageEntity>

    @Delete
    suspend fun deleteMessage(
        message: ChatMessageEntity
    )
}
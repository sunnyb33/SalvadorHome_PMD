package com.example.salvadorhome.data.local.dao

import androidx.room.*
import com.example.salvadorhome.data.model.ConversationEntity

@Dao
interface ConversationDao {

    @Insert
    suspend fun insertConversation(
        conversation: ConversationEntity
    )

    @Query("SELECT * FROM conversations")
    suspend fun getAllConversations():
            List<ConversationEntity>

    @Query("""
        SELECT * FROM conversations
        WHERE id = :conversationId
    """)
    suspend fun getConversationById(
        conversationId: Int
    ): ConversationEntity?

    @Delete
    suspend fun deleteConversation(
        conversation: ConversationEntity
    )
}
package com.example.salvadorhome.data.repository

import com.example.salvadorhome.data.local.dao.ChatMessageDao
import com.example.salvadorhome.data.model.ChatMessageEntity

class ChatMessageRepository(
    private val chatMessageDao: ChatMessageDao
) {

    suspend fun addMessage(
        message: ChatMessageEntity
    ) {
        chatMessageDao.insertMessage(message)
    }

    suspend fun getMessagesByConversation(
        conversationId: Int
    ): List<ChatMessageEntity> {
        return chatMessageDao
            .getMessagesByConversation(
                conversationId
            )
    }

    suspend fun deleteMessage(
        message: ChatMessageEntity
    ) {
        chatMessageDao.deleteMessage(
            message
        )
    }
}
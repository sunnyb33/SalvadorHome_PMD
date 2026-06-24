package com.example.salvadorhome.data.repository

import com.example.salvadorhome.data.local.dao.ConversationDao
import com.example.salvadorhome.data.model.ConversationEntity

class ConversationRepository(
    private val conversationDao: ConversationDao
) {

    suspend fun addConversation(
        conversation: ConversationEntity
    ) {
        conversationDao.insertConversation(conversation)
    }

    suspend fun getAllConversations():
            List<ConversationEntity> {
        return conversationDao.getAllConversations()
    }

    suspend fun getConversationById(
        conversationId: Int
    ): ConversationEntity? {
        return conversationDao.getConversationById(
            conversationId
        )
    }

    suspend fun deleteConversation(
        conversation: ConversationEntity
    ) {
        conversationDao.deleteConversation(
            conversation
        )
    }
}
package com.example.salvadorhome.data.repository

import com.example.salvadorhome.data.local.dao.UserDao
import com.example.salvadorhome.data.model.UserEntity

class UserRepository(
    private val userDao: UserDao
) {

    suspend fun registerUser(user: UserEntity) {
        userDao.insertUser(user)
    }

    suspend fun getUserByEmail(email: String): UserEntity? {
        return userDao.getUserByEmail(email)
    }

    suspend fun login(
        email: String,
        password: String
    ): UserEntity? {
        return userDao.login(email, password)
    }
}
package com.example.salvadorhome.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.salvadorhome.data.model.UserEntity

@Dao
interface UserDao {

    @Insert
    suspend fun insertUser(user: UserEntity)

    @Query("SELECT * FROM users")
    suspend fun getAllUsers(): List<UserEntity>

    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    suspend fun getUserByEmail(email: String): UserEntity?

    @Query("""
        SELECT * FROM users
        WHERE email = :email
        AND password = :password
        LIMIT 1
    """)
    suspend fun login(
        email: String,
        password: String
    ): UserEntity?

    @Delete
    suspend fun deleteUser(user: UserEntity)
}
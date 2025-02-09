package com.example.hw6_room.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.hw6_room.data.entities.UserEntity


@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveUserData(user: UserEntity) : Long

    @Query("SELECT * FROM users WHERE id=:id")
    suspend fun getUserById(id: String) : UserEntity?

    @Query("SELECT * FROM users WHERE email=:email AND password =:password")
    suspend fun getUserByEmailAndPassword(email:String, password: String): UserEntity?
}
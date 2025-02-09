package com.example.hw6_room.data.repository

import com.example.hw6_room.data.dao.UserDao
import com.example.hw6_room.data.entities.UserEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepository(
    private val userDao: UserDao,
    private val ioDispatcher: CoroutineDispatcher
) {
    suspend fun getUserById(id: String) : UserEntity?{
        return withContext(ioDispatcher){
            userDao.getUserById(id = id) ?: throw IllegalStateException("User not found")
        }
    }

    suspend fun saveUser(user: UserEntity) : Long{
        return withContext(ioDispatcher){
            userDao.saveUserData(user = user)
        }
    }

    suspend fun getUserByEmailAndPassword(email:String, password: String): UserEntity?{
        return withContext(ioDispatcher){
            userDao.getUserByEmailAndPassword(email = email, password = password)
        }
    }
}
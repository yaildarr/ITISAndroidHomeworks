package com.example.hw6_room.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.hw6_room.data.dao.TransactionDao
import com.example.hw6_room.data.dao.UserDao
import com.example.hw6_room.data.entities.TransactionEntity
import com.example.hw6_room.data.entities.UserEntity


@Database(
    entities = [UserEntity::class, TransactionEntity::class],
    version = 2
)
abstract class FinanceDatabase : RoomDatabase() {
    abstract val userDao: UserDao
    abstract val transactionDao: TransactionDao
}
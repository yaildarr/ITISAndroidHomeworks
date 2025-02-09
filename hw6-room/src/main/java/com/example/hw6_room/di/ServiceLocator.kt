package com.example.hw6_room.di

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import com.example.hw6_room.data.FinanceDatabase
import com.example.hw6_room.data.dao.UserDao
import com.example.hw6_room.data.migrations.MIGRATION_1_2
import com.example.hw6_room.data.repository.TransactionRepository
import com.example.hw6_room.data.repository.UserRepository
import com.example.hw6_room.util.AuthPreferences
import kotlinx.coroutines.Dispatchers

object ServiceLocator {

    private const val DATABASE_NAME = "FinanceDB"

    private var dbInstance: FinanceDatabase? = null

    private var userRepository: UserRepository? = null
    private var transactionRepository: TransactionRepository? = null

    private var authPreferences: AuthPreferences? = null

    private fun initDatabase(ctx: Context){
         dbInstance = Room.databaseBuilder(ctx,FinanceDatabase::class.java ,DATABASE_NAME)
             .addMigrations(MIGRATION_1_2)
             .build()
    }

    fun initDataLayerDependencies(ctx: Context){
        if (dbInstance == null){
            initDatabase(ctx)
            dbInstance?.let{
                userRepository = UserRepository(it.userDao,Dispatchers.IO)
                transactionRepository = TransactionRepository(it.transactionDao,Dispatchers.IO)
            }
        }
    }

    fun getUserRepository(): UserRepository = userRepository ?: throw IllegalStateException("UserRepository not initialized")
    fun getTransactionRepository(): TransactionRepository = transactionRepository ?: throw IllegalStateException("TransactionRepository not initialized")
}
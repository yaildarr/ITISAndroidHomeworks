package com.example.hw6_room.data.repository

import com.example.hw6_room.data.dao.TransactionDao
import com.example.hw6_room.data.entities.TransactionEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TransactionRepository(
    private val transactionDao: TransactionDao,
    private val ioDispatcher: CoroutineDispatcher
) {
    suspend fun getTransactionById(id: String) : TransactionEntity?{
        return withContext(ioDispatcher){
            transactionDao.getTransactionsById(id) ?: throw IllegalStateException("Transaction not found")
        }
    }
    suspend fun getTransactionsByUserId(userId: String) : List<TransactionEntity>?{
        return withContext(ioDispatcher){
            transactionDao.getTransactionsByUserId(userId)
        }
    }
    suspend fun saveTransaction(transactionEntity: TransactionEntity){
        return withContext(ioDispatcher){
            transactionDao.saveTransactionData(transactionEntity)
        }
    }
    suspend fun deleteTransactionById(id: String): Int{
        return withContext(ioDispatcher){
            transactionDao.deleteTransactionById(id)
        }
    }
}
package com.example.hw6_room.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.hw6_room.data.entities.TransactionEntity


@Dao
interface TransactionDao  {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveTransactionData(transaction: TransactionEntity)

    @Query("SELECT * FROM transactions WHERE id = :id")
    suspend fun getTransactionsById(id : String): TransactionEntity?

    @Query("SELECT * FROM transactions WHERE user_id = :id")
    suspend fun getTransactionsByUserId(id : String) : List<TransactionEntity>

    @Query("DELETE FROM transactions WHERE id = :transactionId")
    suspend fun deleteTransactionById(transactionId: String): Int
}
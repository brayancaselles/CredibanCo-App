package com.brayandev.credibancoapp.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.brayandev.credibancoapp.data.local.database.entities.TransactionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransaction(transaction: TransactionEntity)

    @Query("DELETE FROM transaction_table WHERE id = :idTransaction")
    suspend fun deleteTransaction(idTransaction: Int)

    @Query("SELECT * FROM transaction_table ORDER BY id DESC")
    fun getAllTransaction(): Flow<List<TransactionEntity>>

    @Query("SELECT * FROM transaction_table WHERE id = :idTransaction")
    suspend fun getTransactionById(idTransaction: Int): TransactionEntity

    @Query("SELECT COUNT(*) FROM transaction_table")
    suspend fun countTransactions(): Int
}

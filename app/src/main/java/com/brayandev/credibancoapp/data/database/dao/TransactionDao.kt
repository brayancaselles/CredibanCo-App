package com.brayandev.credibancoapp.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.brayandev.credibancoapp.data.database.entities.TransactionEntity

@Dao
interface TransactionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransaction(transaction: TransactionEntity)

    @Query("DELETE FROM transaction_table WHERE id = :idTransaction")
    suspend fun deleteTransaction(idTransaction: Int)

    @Query("SELECT * FROM transaction_table ORDER BY id DESC")
    suspend fun getAllTransaction(): List<TransactionEntity>
}

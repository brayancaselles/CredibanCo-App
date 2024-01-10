package com.brayandev.credibancoapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.brayandev.credibancoapp.data.database.dao.TransactionDao
import com.brayandev.credibancoapp.data.database.entities.TransactionEntity

@Database(entities = [TransactionEntity::class], version = 1)
abstract class TransactionDataBase : RoomDatabase() {
    abstract fun transactionDao(): TransactionDao
}

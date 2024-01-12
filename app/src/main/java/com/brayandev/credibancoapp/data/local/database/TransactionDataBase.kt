package com.brayandev.credibancoapp.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.brayandev.credibancoapp.data.local.database.dao.TransactionDao
import com.brayandev.credibancoapp.data.local.database.entities.TransactionEntity

@Database(entities = [TransactionEntity::class], version = 1)
abstract class TransactionDataBase : RoomDatabase() {
    abstract fun transactionDao(): TransactionDao
}

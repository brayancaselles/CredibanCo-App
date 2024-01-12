package com.brayandev.credibancoapp.data.local

import android.util.Log
import com.brayandev.credibancoapp.data.local.database.dao.TransactionDao
import com.brayandev.credibancoapp.data.local.database.entities.TransactionEntity
import com.brayandev.credibancoapp.domain.model.TransactionModel
import com.brayandev.credibancoapp.domain.model.toDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalDataSource @Inject constructor(private val dao: TransactionDao) {

    fun getAllTransactions(): Flow<List<TransactionModel>> {
        return dao.getAllTransaction().map { transactions -> transactions.map { it.toDomain() } }
    }

    suspend fun insertTransaction(transactions: TransactionEntity) {
        dao.insertTransaction(transactions)
    }

    suspend fun annulationTransaction(idTransaction: Int) {
        Log.d("annulationTransaction", "idTransaction: $idTransaction")
        dao.deleteTransaction(idTransaction)
    }

    suspend fun countTransactions(): Int {
        return dao.countTransactions()
    }
}

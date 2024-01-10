package com.brayandev.credibancoapp.data

import com.brayandev.credibancoapp.data.database.dao.TransactionDao
import com.brayandev.credibancoapp.data.network.ApiService
import com.brayandev.credibancoapp.domain.model.TransactionModel
import com.brayandev.credibancoapp.domain.model.toDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TransactionRepository @Inject constructor(
    private val apiService: ApiService,
    private val transactionDao: TransactionDao,
) {
    fun getAllTransactionsFromDatabase(): Flow<List<TransactionModel>> = flow {
        val transactions = transactionDao.getAllTransaction()
        emit(transactions.map { it.toDomain() })
    }
}

package com.brayandev.credibancoapp.data

import com.brayandev.credibancoapp.data.database.dao.TransactionDao
import com.brayandev.credibancoapp.data.database.entities.TransactionEntity
import com.brayandev.credibancoapp.data.database.entities.toDataBase
import com.brayandev.credibancoapp.data.network.ApiService
import com.brayandev.credibancoapp.data.network.dto.AnnulationTransactionDto
import com.brayandev.credibancoapp.data.network.dto.TransactionDto
import com.brayandev.credibancoapp.domain.AnnulationTransactionModel
import com.brayandev.credibancoapp.domain.model.TransactionModel
import com.brayandev.credibancoapp.domain.model.toDomain
import com.brayandev.credibancoapp.ui.transactionAuthorization.EnumResponseService
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

    suspend fun requestTransactionDataByTransactionInfoFromApi(
        model: TransactionDto,
    ): EnumResponseService {
        var enum: EnumResponseService = EnumResponseService.IS_DEFAULT
        runCatching { apiService.createTransaction(model) }.onSuccess {
            insertTransaction(it.toDataBase())
            enum = EnumResponseService.IS_SUCCESS
        }.onFailure {
            enum = EnumResponseService.IS_FAILURE
        }
        return enum
    }

    private suspend fun insertTransaction(transactions: TransactionEntity) {
        transactionDao.insertTransaction(transactions)
    }

    suspend fun requestAnnulationTransaction(
        idTransaction: Int,
        model: AnnulationTransactionDto,
    ): EnumResponseService {
        var enum: EnumResponseService = EnumResponseService.IS_DEFAULT
        runCatching { apiService.annulationTransaction(model) }.onSuccess {
            deleteTransaction(idTransaction)
            enum = EnumResponseService.IS_SUCCESS
        }.onFailure { enum = EnumResponseService.IS_FAILURE }

        return enum
    }

    private suspend fun deleteTransaction(idTransaction: Int) {
        transactionDao.deleteTransaction(idTransaction)
    }
}

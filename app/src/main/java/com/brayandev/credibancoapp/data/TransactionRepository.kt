package com.brayandev.credibancoapp.data

import com.brayandev.credibancoapp.data.local.LocalDataSource
import com.brayandev.credibancoapp.data.local.database.entities.toDataBase
import com.brayandev.credibancoapp.data.remote.RemoteDataSource
import com.brayandev.credibancoapp.data.remote.network.dto.AnnulationTransactionDto
import com.brayandev.credibancoapp.data.remote.network.dto.TransactionDto
import com.brayandev.credibancoapp.domain.model.TransactionModel
import com.brayandev.credibancoapp.ui.transactionAuthorization.EnumResponseService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TransactionRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
) {

    val transactions: Flow<List<TransactionModel>> = localDataSource.transactions

    suspend fun requestTransaction(model: TransactionDto): Boolean {
        val result = remoteDataSource.requestTransaction(model)
        if (result.second.statusCode == "00") {
            localDataSource.insertTransaction(result.second.toDataBase())
        }
        return result.second.statusCode == "00"
    }

    suspend fun requestAnnulationTransaction(
        idTransaction: Int,
        model: AnnulationTransactionDto,
    ): EnumResponseService {
        val result = remoteDataSource.annulationTransaction(model)
        if (result.second.statusCode == "00") {
            localDataSource.annulationTransaction(idTransaction)
        }

        return result.first
    }
}

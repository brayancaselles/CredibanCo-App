package com.brayandev.credibancoapp.domain

import com.brayandev.credibancoapp.data.TransactionRepository
import com.brayandev.credibancoapp.domain.model.TransactionModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTransactionUseCase @Inject constructor(private val repository: TransactionRepository) {

    fun getAllTransaction(): Flow<List<TransactionModel>> {
        return repository.transactions
    }
}

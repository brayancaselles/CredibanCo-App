package com.brayandev.credibancoapp.domain

import com.brayandev.credibancoapp.data.TransactionRepository
import javax.inject.Inject

class GetTransactionUseCase @Inject constructor(private val repository: TransactionRepository) {

    operator fun invoke() = repository.getAllTransactionsFromDatabase()
}

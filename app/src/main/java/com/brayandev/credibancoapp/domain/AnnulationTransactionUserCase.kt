package com.brayandev.credibancoapp.domain

import com.brayandev.credibancoapp.data.TransactionRepository
import com.brayandev.credibancoapp.data.remote.network.dto.AnnulationTransactionDto
import com.brayandev.credibancoapp.ui.transactionAuthorization.EnumResponseService
import javax.inject.Inject

class AnnulationTransactionUserCase @Inject constructor(private val repository: TransactionRepository) {

    suspend fun annulationTransaction(
        idTransaction: Int,
        receiptNumber: String,
        transactionIdentifier: String,
    ): EnumResponseService {
        return repository.requestAnnulationTransaction(
            idTransaction,
            AnnulationTransactionDto(receiptId = receiptNumber, rrn = transactionIdentifier),
        )
    }
}

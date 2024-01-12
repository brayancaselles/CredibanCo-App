package com.brayandev.credibancoapp.domain

import com.brayandev.credibancoapp.data.TransactionRepository
import com.brayandev.credibancoapp.data.remote.network.dto.TransactionDto
import javax.inject.Inject

class RequestTransactionUseCase @Inject constructor(private val repository: TransactionRepository) {

    suspend fun requestTransaction(
        commerceCode: String,
        terminalCode: String,
        amount: String,
        card: String,
    ): Boolean {
        val amountReplace = amount.replace(Regex("[$,.]"), "")

        return repository.requestTransaction(
            TransactionDto(
                commerceCode = commerceCode,
                terminalCode = terminalCode,
                amount = amountReplace,
                card = card,
            ),
        )
    }
}

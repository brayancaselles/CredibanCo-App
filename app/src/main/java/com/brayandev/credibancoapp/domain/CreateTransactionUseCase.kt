package com.brayandev.credibancoapp.domain

import com.brayandev.credibancoapp.data.TransactionRepository
import com.brayandev.credibancoapp.data.network.dto.TransactionDto
import com.brayandev.credibancoapp.ui.transactionAuthorization.EnumResponseService
import javax.inject.Inject

class CreateTransactionUseCase @Inject constructor(private val repository: TransactionRepository) {

    suspend fun createTransaction(
        commerceCode: String,
        terminalCode: String,
        amount: String,
        card: String,
    ): EnumResponseService {
        val amountReplace = amount.replace(Regex("[$,.]"), "")

        return repository.requestTransactionDataByTransactionInfoFromApi(
            TransactionDto(
                commerceCode = commerceCode,
                terminalCode = terminalCode,
                amount = amountReplace,
                card = card,
            ),
        )
    }
}

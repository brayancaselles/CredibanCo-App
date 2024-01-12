package com.brayandev.credibancoapp.domain

import android.util.Log
import com.brayandev.credibancoapp.data.TransactionRepository
import com.brayandev.credibancoapp.data.remote.network.dto.TransactionDto
import javax.inject.Inject

class RequestTransactionUseCase @Inject constructor(private val repository: TransactionRepository) {

    suspend fun createTransaction(
        commerceCode: String,
        terminalCode: String,
        amount: String,
        card: String,
    ): Boolean {
        val amountReplace = amount.replace(Regex("[$,.]"), "")

        val result = repository.requestTransaction(
            TransactionDto(
                commerceCode = commerceCode,
                terminalCode = terminalCode,
                amount = amountReplace,
                card = card,
            ),
        )
        Log.d("tagggggggggggg", " use case result $result")
        return result
    }
}

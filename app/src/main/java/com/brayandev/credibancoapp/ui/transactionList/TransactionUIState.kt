package com.brayandev.credibancoapp.ui.transactionList

import com.brayandev.credibancoapp.domain.model.TransactionModel

sealed class TransactionUIState {
    data object Loading : TransactionUIState()
    data class Success(val transactionList: List<TransactionModel>) : TransactionUIState()
    data class Error(val message: String) : TransactionUIState()
}

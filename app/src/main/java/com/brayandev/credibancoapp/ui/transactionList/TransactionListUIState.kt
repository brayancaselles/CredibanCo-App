package com.brayandev.credibancoapp.ui.transactionList

import com.brayandev.credibancoapp.domain.model.TransactionModel

sealed class TransactionListUIState {
    data object Loading : TransactionListUIState()
    data class Success(val transactionList: List<TransactionModel>) : TransactionListUIState()
    data class Error(val message: String) : TransactionListUIState()
}

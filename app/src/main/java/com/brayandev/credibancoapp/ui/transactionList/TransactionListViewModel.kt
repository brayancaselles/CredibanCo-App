package com.brayandev.credibancoapp.ui.transactionList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brayandev.credibancoapp.domain.GetTransactionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionListViewModel @Inject constructor(private val getAllTransactionsUseCase: GetTransactionUseCase) :
    ViewModel() {

    private val _transactions =
        MutableStateFlow<TransactionListUIState>(TransactionListUIState.Loading)
    val transactions: StateFlow<TransactionListUIState> = _transactions

    fun getTransactionList() {
        viewModelScope.launch {
            getAllTransactionsUseCase.getAllTransaction()
                .catch {
                    it.message?.let { error ->
                        _transactions.value = TransactionListUIState.Error(error)
                    }
                }
                .flowOn(Dispatchers.IO)
                .collect {
                    _transactions.value = TransactionListUIState.Success(it)
                }
        }
    }
}

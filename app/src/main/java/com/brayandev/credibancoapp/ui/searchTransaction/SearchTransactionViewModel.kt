package com.brayandev.credibancoapp.ui.searchTransaction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brayandev.credibancoapp.domain.GetTransactionUseCase
import com.brayandev.credibancoapp.ui.transactionList.TransactionListUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchTransactionViewModel @Inject constructor(private val getAllTransactionUseCase: GetTransactionUseCase) :
    ViewModel() {

    private val _transactions =
        MutableStateFlow<TransactionListUIState>(TransactionListUIState.Loading)
    val transactions: StateFlow<TransactionListUIState> = _transactions

    fun getTransactionList() {
        viewModelScope.launch {
            getAllTransactionUseCase.invoke().apply {
                catch {
                    _transactions.value =
                        it.message?.let { error -> TransactionListUIState.Error(error) }!!
                }
                flowOn(Dispatchers.Main)
                collect { _transactions.value = TransactionListUIState.Success(it) }
            }
        }
    }
}

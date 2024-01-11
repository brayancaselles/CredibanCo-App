package com.brayandev.credibancoapp.ui.transactionDetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brayandev.credibancoapp.domain.AnnulationTransactionUserCase
import com.brayandev.credibancoapp.ui.transactionAuthorization.EnumResponseService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionDetailViewModel @Inject constructor(private val annulationTransactionUseCase: AnnulationTransactionUserCase) :
    ViewModel() {
    private val _showDialog = MutableLiveData<EnumResponseService>()
    val showDialog: LiveData<EnumResponseService> = _showDialog

    fun onDataSelected(
        idTransaction: Int,
        receiptNumber: String,
        transactionIdentifier: String,
    ) {
        viewModelScope.launch {
            _showDialog.postValue(
                annulationTransactionUseCase.annulationTransaction(
                    idTransaction,
                    receiptNumber,
                    transactionIdentifier,
                ),
            )
        }
    }
}

package com.brayandev.credibancoapp.ui.transactionAuthorization

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brayandev.credibancoapp.domain.RequestTransactionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class TransactionAuthorizationViewModel @Inject constructor(private val createTransactionUseCase: RequestTransactionUseCase) :
    ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    fun onDataSelected(
        commerceCode: String,
        terminalCode: String,
        amount: String,
        card: String,
        showDialog: () -> Unit,
        isErrorRequest: (Boolean) -> Unit,
    ) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val result = withContext(Dispatchers.IO) {
                    createTransactionUseCase.createTransaction(
                        commerceCode,
                        terminalCode,
                        amount,
                        card,
                    )
                }

                if (result) {
                    showDialog()
                } else {
                    isErrorRequest(true)
                }
            } catch (e: Exception) {
                isErrorRequest(false)
            }
            _isLoading.value = false
        }
    }
}

enum class EnumResponseService {
    IS_SUCCESS,
    IS_FAILURE,
    IS_DEFAULT,
}

package com.brayandev.credibancoapp.ui.transactionAuthorization

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brayandev.credibancoapp.domain.CreateTransactionUseCase
import com.brayandev.credibancoapp.domain.model.Loading
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionAuthorizationViewModel @Inject constructor(private val createTransactionUseCase: CreateTransactionUseCase) :
    ViewModel() {

    private companion object {
        const val EQUAL_CHARS_LENGTH = 6
        const val MIN_CHARS_LENGTH = 0.00
        const val MAX_CHARS_LENGTH = 16
    }

    private val _viewFieldState = MutableStateFlow(ValidCharacterUiState())
    val viewFieldState: StateFlow<ValidCharacterUiState> get() = _viewFieldState

    private val _showDialog = MutableLiveData<EnumResponseService>()
    val showDialog: LiveData<EnumResponseService> get() = _showDialog

    private val _showLoading: MutableStateFlow<Loading> by lazy { MutableStateFlow(Loading()) }
    val showLoading: StateFlow<Loading> get() = _showLoading

    fun onFieldsChanged(commerceCode: String, terminalCode: String, amount: String, card: String) {
        _viewFieldState.value = ValidCharacterUiState(
            isValidCommerceCode = isValidForSixChars(commerceCode),
            isValidTerminalCode = isValidForSixChars(terminalCode),
            isValidAmount = isValidForTwoChars(amount),
            isValidCard = isValidForSixteenChars(card),
        )
    }

    private fun isValidForSixChars(string: String): Boolean =
        string.length >= EQUAL_CHARS_LENGTH || string.isEmpty()

    private fun isValidForTwoChars(string: String): Boolean =
        string.length > MIN_CHARS_LENGTH || string.isEmpty()

    private fun isValidForSixteenChars(string: String): Boolean =
        string.length >= MAX_CHARS_LENGTH || string.isEmpty()

    fun onDataSelected(
        commerceCode: String,
        terminalCode: String,
        amount: String,
        card: String,
    ) {
        if (isValidForSixChars(commerceCode) && isValidForSixChars(terminalCode) && isValidForTwoChars(
                amount,
            ) && isValidForSixteenChars(card)
        ) {
            createTransaction(commerceCode, terminalCode, amount, card)
        } else {
            onFieldsChanged(commerceCode, terminalCode, amount, card)
        }
    }

    private fun createTransaction(
        commerceCode: String,
        terminalCode: String,
        amount: String,
        card: String,
    ) {
        viewModelScope.launch {
            _showLoading.update { currentState ->
                currentState.copy(showLoading = true)
            }
            _showDialog.postValue(
                createTransactionUseCase.createTransaction(
                    commerceCode,
                    terminalCode,
                    amount,
                    card,
                ),
            )
            _showLoading.update { currentState ->
                currentState.copy(showLoading = false)
            }
        }
    }
}

enum class EnumResponseService {
    IS_SUCCESS,
    IS_FAILURE,
    IS_DEFAULT,
}

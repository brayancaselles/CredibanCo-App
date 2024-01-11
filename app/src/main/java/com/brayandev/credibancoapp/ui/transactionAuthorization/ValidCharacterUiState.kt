package com.brayandev.credibancoapp.ui.transactionAuthorization

data class ValidCharacterUiState(
    val isValidCommerceCode: Boolean = true,
    val isValidTerminalCode: Boolean = true,
    val isValidAmount: Boolean = true,
    val isValidCard: Boolean = true,
)

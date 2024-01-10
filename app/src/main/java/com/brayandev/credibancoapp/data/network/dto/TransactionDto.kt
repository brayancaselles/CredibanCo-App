package com.brayandev.credibancoapp.data.network.dto

import java.util.UUID

data class TransactionDto(
    val id: String = UUID.randomUUID().toString(),
    val commerceCode: String,
    val terminalCode: String,
    val amount: String,
    val card: String,
)

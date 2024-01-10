package com.brayandev.credibancoapp.domain.model

import com.brayandev.credibancoapp.data.database.entities.TransactionEntity

data class TransactionModel(
    val id: Int,
    val receiptNumber: String,
    val transactionIdentifier: String,
    val statusCode: String,
    val statusDescription: String,
)

fun TransactionEntity.toDomain() = TransactionModel(
    id = id,
    receiptNumber = receiptNumber,
    transactionIdentifier = transactionIdentifier,
    statusCode = statusCode,
    statusDescription = statusDescription,
)

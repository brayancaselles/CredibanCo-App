package com.brayandev.credibancoapp.data.network.response

import com.google.gson.annotations.SerializedName

data class TransactionResponse(
    @SerializedName("receiptId") val receiptNumber: String,
    @SerializedName("rrn") val transactionIdentifier: String,
    @SerializedName("statusCode") val statusCode: String,
    @SerializedName("statusDescription") val statusDescription: String,
)

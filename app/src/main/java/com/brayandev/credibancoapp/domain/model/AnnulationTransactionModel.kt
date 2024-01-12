package com.brayandev.credibancoapp.domain.model

import com.brayandev.credibancoapp.data.remote.network.response.AnnulationTransactionResponse

data class AnnulationTransactionModel(val statusCode: String, val statusDescription: String)

fun AnnulationTransactionResponse.toDomain() = AnnulationTransactionModel(
    statusCode = statusCode,
    statusDescription = statusDescription,
)

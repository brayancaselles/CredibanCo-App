package com.brayandev.credibancoapp.data.remote.network

import com.brayandev.credibancoapp.data.remote.network.dto.AnnulationTransactionDto
import com.brayandev.credibancoapp.data.remote.network.dto.TransactionDto
import com.brayandev.credibancoapp.data.remote.network.response.AnnulationTransactionResponse
import com.brayandev.credibancoapp.data.remote.network.response.TransactionResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("authorization")
    suspend fun createTransaction(
        @Body transactionData: TransactionDto,
    ): TransactionResponse

    @POST("annulment")
    suspend fun annulationTransaction(
        @Body transactionData: AnnulationTransactionDto,
    ): AnnulationTransactionResponse
}

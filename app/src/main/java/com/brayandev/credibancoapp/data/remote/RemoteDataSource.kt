package com.brayandev.credibancoapp.data.remote

import com.brayandev.credibancoapp.data.remote.network.ApiService
import com.brayandev.credibancoapp.data.remote.network.dto.AnnulationTransactionDto
import com.brayandev.credibancoapp.data.remote.network.dto.TransactionDto
import com.brayandev.credibancoapp.data.remote.network.response.TransactionResponse
import com.brayandev.credibancoapp.domain.model.AnnulationTransactionModel
import com.brayandev.credibancoapp.domain.model.toDomain
import com.brayandev.credibancoapp.ui.transactionAuthorization.EnumResponseService
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val service: ApiService) {

    suspend fun requestTransaction(
        model: TransactionDto,
    ): Pair<EnumResponseService, TransactionResponse> {
        var modelTransaction = TransactionResponse("", "", "", "")
        var enum: EnumResponseService = EnumResponseService.IS_DEFAULT
        runCatching { service.createTransaction(model) }.onSuccess {
            modelTransaction = it
            enum = EnumResponseService.IS_SUCCESS
        }.onFailure {
            enum = EnumResponseService.IS_FAILURE
        }
        return Pair(enum, modelTransaction)
    }

    suspend fun annulationTransaction(
        model: AnnulationTransactionDto,
    ): Pair<EnumResponseService, AnnulationTransactionModel> {
        var modelAnnulation = AnnulationTransactionModel("", "")
        var enum: EnumResponseService = EnumResponseService.IS_DEFAULT
        runCatching { service.annulationTransaction(model) }.onSuccess {
            modelAnnulation = it.toDomain()
            enum = EnumResponseService.IS_SUCCESS
        }.onFailure {
            enum = EnumResponseService.IS_FAILURE
        }

        return Pair(enum, modelAnnulation)
    }
}

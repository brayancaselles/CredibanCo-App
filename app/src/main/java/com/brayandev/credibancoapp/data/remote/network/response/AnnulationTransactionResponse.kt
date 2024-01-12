package com.brayandev.credibancoapp.data.remote.network.response

import com.google.gson.annotations.SerializedName

data class AnnulationTransactionResponse(
    @SerializedName("statusCode") val statusCode: String,
    @SerializedName("statusDescription") val statusDescription: String,
)

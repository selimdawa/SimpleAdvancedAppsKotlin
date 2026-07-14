package com.flatcode.simpleadvancedapps.crypto.model.errorResponse

import com.google.gson.annotations.SerializedName

data class ErrorResponse(@SerializedName("status") val status: Status?)

data class Status(
    @SerializedName("error_code") val errorCode: Int?,
    @SerializedName("error_message") val errorMessage: String?,
    @SerializedName("timestamp") val timestamp: String?
)
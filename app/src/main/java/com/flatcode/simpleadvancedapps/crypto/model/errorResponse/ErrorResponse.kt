package com.flatcode.simpleadvancedapps.crypto.model.errorResponse

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ErrorResponse(@SerializedName("status") val status: Status?) : Parcelable

@Parcelize
data class Status(
    @SerializedName("error_code") val errorCode: Int?,
    @SerializedName("error_message") val errorMessage: String?,
    @SerializedName("timestamp") val timestamp: String?
) : Parcelable

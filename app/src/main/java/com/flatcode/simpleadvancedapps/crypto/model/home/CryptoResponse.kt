package com.flatcode.simpleadvancedapps.crypto.model.home

import com.google.gson.annotations.SerializedName

data class CryptoResponse(
    @SerializedName("data") val data: List<Data>?, @SerializedName("status") val status: Status?
)

data class Data(
    @SerializedName("id") val id: Int?,
    @SerializedName("name") val name: String?,
    @SerializedName("symbol") val symbol: String?,
    @SerializedName("quote") val quote: Quote?
)

data class Quote(@SerializedName("USD") val uSD: USD?)

data class USD(@SerializedName("price") val price: Double?)

data class Status(
    @SerializedName("error_code") val errorCode: Int?,
    @SerializedName("error_message") val errorMessage: String?,
    @SerializedName("timestamp") val timestamp: String?
)
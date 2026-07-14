package com.flatcode.simpleadvancedapps.crypto.model.detail

import com.google.gson.annotations.SerializedName

data class DetailResponse(
    @SerializedName("data") val data: Map<String, List<CoinDetail>>?,
    @SerializedName("status") val status: Status?
)

data class CoinDetail(
    @SerializedName("id") val id: Int?,
    @SerializedName("name") val name: String?,
    @SerializedName("symbol") val symbol: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("logo") val logo: String?,
    @SerializedName("urls") val urls: Urls?
)

data class Urls(
    @SerializedName("website") val website: List<String>?,
    @SerializedName("twitter") val twitter: List<String>?
)

data class Status(
    @SerializedName("error_code") val errorCode: Int?,
    @SerializedName("error_message") val errorMessage: String?,
    @SerializedName("timestamp") val timestamp: String?
)
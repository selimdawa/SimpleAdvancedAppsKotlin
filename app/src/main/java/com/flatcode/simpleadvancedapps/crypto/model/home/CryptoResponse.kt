package com.flatcode.simpleadvancedapps.crypto.model.home

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class CryptoResponse(
    @SerializedName("data") val data: List<Data>?, @SerializedName("status") val status: Status?
) : Parcelable

@Parcelize
data class Data(
    @SerializedName("id") val id: Int?,
    @SerializedName("name") val name: String?,
    @SerializedName("symbol") val symbol: String?,
    @SerializedName("quote") val quote: Quote?
) : Parcelable

@Parcelize
data class Quote(@SerializedName("USD") val uSD: USD?) : Parcelable

@Parcelize
data class USD(@SerializedName("price") val price: Double?) : Parcelable

@Parcelize
data class Status(
    @SerializedName("error_code") val errorCode: Int?,
    @SerializedName("error_message") val errorMessage: String?,
    @SerializedName("timestamp") val timestamp: String?
) : Parcelable
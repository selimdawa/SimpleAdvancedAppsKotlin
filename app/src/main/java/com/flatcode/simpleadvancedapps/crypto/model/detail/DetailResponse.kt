package com.flatcode.simpleadvancedapps.crypto.model.detail

import com.google.gson.annotations.SerializedName

data class DetailResponse(
    @SerializedName("data")
    val `data`: Any?,
    @SerializedName("status")
    val status: Status?
)
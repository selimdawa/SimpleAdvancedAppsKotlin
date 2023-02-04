package com.flatcode.simpleadvancedapps.rickAndMorty.models

import com.google.gson.annotations.SerializedName

data class Info(
    @SerializedName("count")
    var count: Int? = null,

    @SerializedName("pages")
    var pages: Int = 1,

    @SerializedName("next")
    val next: String? = null,

    @SerializedName("prev")
    val prev: String? = null
)
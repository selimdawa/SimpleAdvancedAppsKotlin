package com.flatcode.simpleadvancedapps.rickAndMorty.data.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Info(
    @SerializedName("count") val count: Int? = null,
    @SerializedName("pages") val pages: Int = 1,
    @SerializedName("next") val next: String? = null,
    @SerializedName("prev") val prev: String? = null
) : Parcelable
package com.flatcode.simpleadvancedapps.news.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class NewsResponse<T>(
    @SerializedName("status") val status: String,
    @SerializedName("totalResult") val totalResult: Int,
    @SerializedName("articles") val articles: List<@RawValue T>
) : Parcelable
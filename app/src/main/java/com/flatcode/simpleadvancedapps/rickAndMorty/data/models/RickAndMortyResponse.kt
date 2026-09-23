package com.flatcode.simpleadvancedapps.rickAndMorty.data.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class RickAndMortyResponse<T>(
    @SerializedName("info") val info: Info,
    @SerializedName("results") val results: List<@RawValue T>
) : Parcelable
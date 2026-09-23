package com.flatcode.simpleadvancedapps.pokemon.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ResultApi(
    @SerializedName("results") val pokemon: List<PokeModel>,
) : Parcelable

@Parcelize
data class PokeModel(
    @SerializedName("name") val name: String,
    @SerializedName("url") val url: String,
) : Parcelable
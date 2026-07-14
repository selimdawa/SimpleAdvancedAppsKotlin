package com.flatcode.simpleadvancedapps.pokemon.data.model

import com.google.gson.annotations.SerializedName

data class ResultApi(
    @SerializedName("results") val pokemon: List<PokeModel>,
)

data class PokeModel(
    @SerializedName("name") val name: String,
    @SerializedName("url") val url: String,
)
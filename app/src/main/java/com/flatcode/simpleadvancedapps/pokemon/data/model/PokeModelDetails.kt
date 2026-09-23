package com.flatcode.simpleadvancedapps.pokemon.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class PokeModelDetails(
    @SerializedName("id") val id: Int,
    @SerializedName("height") val height: Int,
    @SerializedName("name") val name: String,
    @SerializedName("sprites") val sprites: Sprites,
    @SerializedName("stats") val pokemonDetails: List<Stats>,
    @SerializedName("types") val types: List<Types>,
    @SerializedName("weight") val weight: Int,
) : Parcelable

@Parcelize
data class Sprites(
    @SerializedName("other") val other: Other,
) : Parcelable

@Parcelize
data class Other(
    @SerializedName("official-artwork") val officialArtwork: OfficialArtwork,
) : Parcelable

@Parcelize
data class OfficialArtwork(
    @SerializedName("front_default") val img: String,
) : Parcelable

@Parcelize
data class Stats(
    @SerializedName("base_stat") val statValue: Int,
    @SerializedName("stat") val stat: Stat,
) : Parcelable

@Parcelize
data class Stat(
    @SerializedName("name") val statName: String,
) : Parcelable

@Parcelize
data class Types(
    @SerializedName("type") val type: Type,
) : Parcelable

@Parcelize
data class Type(
    @SerializedName("name") val name: String,
) : Parcelable
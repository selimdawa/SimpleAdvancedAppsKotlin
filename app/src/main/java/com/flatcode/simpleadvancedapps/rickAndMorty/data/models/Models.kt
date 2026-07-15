package com.flatcode.simpleadvancedapps.rickAndMorty.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.flatcode.simpleadvancedapps.rickAndMorty.ui.base.IBaseDiffModel

data class RickAndMortyResponse<T>(
    @SerializedName("info")
    val info: Info,
    @SerializedName("results")
    val results: List<T>
)

data class Info(
    @SerializedName("count")
    val count: Int? = null,
    @SerializedName("pages")
    val pages: Int = 1,
    @SerializedName("next")
    val next: String? = null,
    @SerializedName("prev")
    val prev: String? = null
)

@Entity(tableName = "characters")
data class CharacterModel(
    @PrimaryKey
    @SerializedName("id")
    override val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("species")
    val species: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("gender")
    val gender: String,
    @SerializedName("image")
    val image: String,
) : IBaseDiffModel<Int>

@Entity(tableName = "episodes")
data class EpisodeModel(
    @PrimaryKey
    @SerializedName("id")
    override val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("air_date")
    val airDate: String,
    @SerializedName("episode")
    val episode: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("created")
    val created: String,
) : IBaseDiffModel<Int>

@Entity(tableName = "locations")
data class LocationModel(
    @PrimaryKey
    @SerializedName("id")
    override val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String,
) : IBaseDiffModel<Int>
package com.flatcode.simpleadvancedapps.rickAndMorty.models.episode

import com.flatcode.simpleadvancedapps.rickAndMorty.base.IBaseDiffModel
import com.google.gson.annotations.SerializedName

data class EpisodeModel(
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

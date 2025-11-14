package com.example.simpleadvancedapps.models.location

import com.flatcode.simpleadvancedapps.rickAndMorty.base.IBaseDiffModel
import com.google.gson.annotations.SerializedName

data class LocationModel(
    override val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String,
) : IBaseDiffModel<Int>
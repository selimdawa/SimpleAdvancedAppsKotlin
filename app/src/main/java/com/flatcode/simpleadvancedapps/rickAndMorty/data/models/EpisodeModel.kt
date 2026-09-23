package com.flatcode.simpleadvancedapps.rickAndMorty.data.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.flatcode.simpleadvancedapps.rickAndMorty.ui.base.IBaseDiffModel
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Entity(tableName = "episodes")
@Parcelize
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
) : IBaseDiffModel<Int>, Parcelable

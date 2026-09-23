package com.flatcode.simpleadvancedapps.rickAndMorty.data.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.flatcode.simpleadvancedapps.rickAndMorty.ui.base.IBaseDiffModel
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Entity(tableName = "characters")
@Parcelize
data class CharacterModel(
    @PrimaryKey @SerializedName("id") override val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("status") val status: String,
    @SerializedName("species") val species: String,
    @SerializedName("type") val type: String,
    @SerializedName("gender") val gender: String,
    @SerializedName("image") val image: String,
) : IBaseDiffModel<Int>, Parcelable
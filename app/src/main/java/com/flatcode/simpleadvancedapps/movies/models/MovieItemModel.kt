package com.flatcode.simpleadvancedapps.movies.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Entity(tableName = "movie_table")
@Parcelize
data class MovieItemModel(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val overview: String,
    @ColumnInfo(name = "poster_path") @SerializedName("poster_path") val posterPath: String,
    @ColumnInfo(name = "release_date") @SerializedName("release_date") val releaseDate: String,
    val title: String,
) : Parcelable
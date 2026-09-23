package com.flatcode.simpleadvancedapps.movies.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class MoviesModel(
    val page: Int,
    val results: List<MovieItemModel>,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("total_results") val totalResults: Int,
) : Parcelable
package com.flatcode.simpleadvancedapps.news.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.flatcode.simpleadvancedapps.news.base.IBaseDiffModel
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Entity(tableName = "top_articles")
@Parcelize
data class TopArticlesNewsItem(
    @PrimaryKey(autoGenerate = true) @SerializedName("id") override val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("urlToImage") val image: String,
) : IBaseDiffModel<Int>, Parcelable
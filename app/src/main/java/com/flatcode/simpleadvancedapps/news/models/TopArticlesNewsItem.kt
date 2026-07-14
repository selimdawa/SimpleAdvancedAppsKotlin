package com.flatcode.simpleadvancedapps.news.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.flatcode.simpleadvancedapps.news.base.IBaseDiffModel
import com.google.gson.annotations.SerializedName

@Entity(tableName = "top_articles")
data class TopArticlesNewsItem(
    @PrimaryKey(autoGenerate = true) @SerializedName("id") override val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("urlToImage") val image: String,
) : IBaseDiffModel<Int>
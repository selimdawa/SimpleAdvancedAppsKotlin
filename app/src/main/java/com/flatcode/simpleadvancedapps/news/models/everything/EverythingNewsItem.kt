package com.flatcode.simpleadvancedapps.news.models.everything

import com.flatcode.simpleadvancedapps.news.base.IBaseDiffModel
import com.google.gson.annotations.SerializedName

data class EverythingNewsItem(
    @SerializedName("id")
    override val id: Int,
    @SerializedName("title")
    val tittle: String,
) : IBaseDiffModel<Int>

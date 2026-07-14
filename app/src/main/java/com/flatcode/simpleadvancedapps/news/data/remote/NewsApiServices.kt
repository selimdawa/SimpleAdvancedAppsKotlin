package com.flatcode.simpleadvancedapps.news.data.remote

import com.flatcode.simpleadvancedapps.news.models.EverythingNewsItem
import com.flatcode.simpleadvancedapps.news.models.NewsResponse
import com.flatcode.simpleadvancedapps.news.models.TopArticlesNewsItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiServices {

    @GET("everything")
    suspend fun fetchEverything(
        @Query("q") query: String
    ): Response<NewsResponse<EverythingNewsItem>>

    @GET("top-headlines")
    suspend fun fetchTopArticles(
        @Query("country") country: String
    ): Response<NewsResponse<TopArticlesNewsItem>>
}
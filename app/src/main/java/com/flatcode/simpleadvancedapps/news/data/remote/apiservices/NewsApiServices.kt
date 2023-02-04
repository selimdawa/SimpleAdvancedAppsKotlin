package com.flatcode.simpleadvancedapps.news.data.remote.apiservices

import com.flatcode.simpleadvancedapps.Unit.DATA
import com.flatcode.simpleadvancedapps.news.models.NewsResponse
import com.flatcode.simpleadvancedapps.news.models.everything.EverythingNewsItem
import com.flatcode.simpleadvancedapps.news.models.toparticles.TopArticlesNewsItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface NewsApiServices {

    @GET("everything")
    suspend fun fetchEverything(
        @Query("q") query: String,
        @Header("X-Api-Key") key: String = DATA.API_NEWS,
    ): Response<NewsResponse<EverythingNewsItem>>

    @GET("top-headlines")
    suspend fun fetchTopArticles(
        @Query("country") country: String,
        @Header("X-Api-Key") key: String = DATA.API_NEWS,
    ): Response<NewsResponse<TopArticlesNewsItem>>
}
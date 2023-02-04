package com.flatcode.simpleadvancedapps.news.data.repositories

import com.flatcode.simpleadvancedapps.news.base.BaseRepository
import com.flatcode.simpleadvancedapps.news.data.remote.apiservices.NewsApiServices
import javax.inject.Inject

class EverythingRepository @Inject constructor(private val service: NewsApiServices) :
    BaseRepository() {

    fun fetchEverything() = doRequest {
        service.fetchEverything("bitcoin")
    }

    fun fetchTopArticles() = doRequest {
        service.fetchTopArticles("us")
    }
}
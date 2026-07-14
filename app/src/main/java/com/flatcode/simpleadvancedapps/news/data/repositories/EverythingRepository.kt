package com.flatcode.simpleadvancedapps.news.data.repositories

import com.flatcode.simpleadvancedapps.news.base.BaseRepository
import com.flatcode.simpleadvancedapps.news.data.local.NewsDao
import com.flatcode.simpleadvancedapps.news.data.remote.NewsApiServices
import javax.inject.Inject

class EverythingRepository @Inject constructor(
    private val service: NewsApiServices, private val newsDao: NewsDao
) : BaseRepository() {

    fun fetchEverything() = doRequest {
        service.fetchEverything("bitcoin")
    }

    fun fetchTopArticles() = doRequest {
        service.fetchTopArticles("us")
    }

    // Example of local database usage
    fun getLocalEverything() = newsDao.getAllEverything()
}
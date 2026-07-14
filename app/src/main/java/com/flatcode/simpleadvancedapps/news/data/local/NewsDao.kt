package com.flatcode.simpleadvancedapps.news.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.flatcode.simpleadvancedapps.news.models.EverythingNewsItem
import com.flatcode.simpleadvancedapps.news.models.TopArticlesNewsItem
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {

    // Everything News
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEverything(news: List<EverythingNewsItem>)

    @Query("SELECT * FROM everything_news")
    fun getAllEverything(): Flow<List<EverythingNewsItem>>

    @Query("DELETE FROM everything_news")
    suspend fun clearEverything()

    // Top Articles
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTopArticles(articles: List<TopArticlesNewsItem>)

    @Query("SELECT * FROM top_articles")
    fun getAllTopArticles(): Flow<List<TopArticlesNewsItem>>

    @Query("DELETE FROM top_articles")
    suspend fun clearTopArticles()
}

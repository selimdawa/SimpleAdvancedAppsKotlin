package com.flatcode.simpleadvancedapps.news.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.flatcode.simpleadvancedapps.news.models.EverythingNewsItem
import com.flatcode.simpleadvancedapps.news.models.TopArticlesNewsItem

@Database(entities = [EverythingNewsItem::class, TopArticlesNewsItem::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun newsDao(): NewsDao
}

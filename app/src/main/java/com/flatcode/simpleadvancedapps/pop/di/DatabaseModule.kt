package com.flatcode.simpleadvancedapps.pop.di

import android.content.Context
import androidx.room.Room
import com.flatcode.simpleadvancedapps.pop.db.PopDao
import com.flatcode.simpleadvancedapps.pop.db.PopDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): PopDatabase {
        return Room.databaseBuilder(
            context, PopDatabase::class.java, "pop_database"
        ).build()
    }

    @Provides
    fun providePopDao(database: PopDatabase): PopDao {
        return database.popDao()
    }
}
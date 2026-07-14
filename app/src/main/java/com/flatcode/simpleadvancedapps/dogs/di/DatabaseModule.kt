package com.flatcode.simpleadvancedapps.dogs.di

import android.content.Context
import androidx.room.Room
import com.flatcode.simpleadvancedapps.dogs.data.AppDatabase
import com.flatcode.simpleadvancedapps.dogs.data.DogDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
@Suppress("unused")
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context, AppDatabase::class.java, "dogs_db"
        ).build()
    }

    @Provides
    fun provideDogDao(database: AppDatabase): DogDao {
        return database.dogDao()
    }
}
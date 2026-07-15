package com.flatcode.simpleadvancedapps.rickAndMorty.di

import android.content.Context
import androidx.room.Room
import com.flatcode.simpleadvancedapps.rickAndMorty.data.local.AppDatabase
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
    fun provideDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, AppDatabase::class.java, "rick_morty_db")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideAppDao(db: AppDatabase) = db.appDao()
}
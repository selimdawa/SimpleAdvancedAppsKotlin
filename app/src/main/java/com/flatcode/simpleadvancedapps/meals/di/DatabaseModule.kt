package com.flatcode.simpleadvancedapps.meals.di

import android.content.Context
import androidx.room.Room
import com.flatcode.simpleadvancedapps.meals.db.MealDao
import com.flatcode.simpleadvancedapps.meals.db.MealDatabase
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
    fun provideMealDatabase(
        @ApplicationContext context: Context
    ): MealDatabase = Room.databaseBuilder(
        context, MealDatabase::class.java, "meal.db"
    ).fallbackToDestructiveMigration(dropAllTables = true).build()

    @Provides
    @Singleton
    fun provideMealDao(
        mealDatabase: MealDatabase
    ): MealDao = mealDatabase.mealDao()
}
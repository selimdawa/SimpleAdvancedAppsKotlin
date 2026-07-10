package com.flatcode.simpleadvancedapps.calculator.di

import android.content.Context
import androidx.room.Room
import com.flatcode.simpleadvancedapps.calculator.data.CalculatorDao
import com.flatcode.simpleadvancedapps.calculator.data.CalculatorDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CalculatorModule {

    @Provides
    @Singleton
    fun provideCalculatorDatabase(
        @ApplicationContext context: Context
    ): CalculatorDatabase {
        return Room.databaseBuilder(
            context,
            CalculatorDatabase::class.java,
            "calculator_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideCalculatorDao(database: CalculatorDatabase): CalculatorDao {
        return database.calculatorDao()
    }
}
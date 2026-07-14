package com.flatcode.simpleadvancedapps.pokemon.di

import android.content.Context
import androidx.room.Room
import com.flatcode.simpleadvancedapps.pokemon.data.database.PokeDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    private const val POKEMON_DATABASE_NAME = "pokemon_database"

    @Singleton
    @Provides
    fun provideRoom(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, PokeDatabase::class.java, POKEMON_DATABASE_NAME).build()

    @Singleton
    @Provides
    fun providePokeDao(db: PokeDatabase) = db.getPokeDao()
}
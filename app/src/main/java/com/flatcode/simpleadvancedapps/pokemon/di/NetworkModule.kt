package com.flatcode.simpleadvancedapps.pokemon.di

import com.flatcode.simpleadvancedapps.pokemon.data.network.ApiClient
import com.flatcode.simpleadvancedapps.utils.DATA
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class PokemonRetrofit

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @PokemonRetrofit
    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(DATA.BASE_URL_POKE)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideApiClient(@PokemonRetrofit retrofit: Retrofit): ApiClient {
        return retrofit.create(ApiClient::class.java)
    }
}
package com.flatcode.simpleadvancedapps.rickAndMorty.di

import com.flatcode.simpleadvancedapps.rickAndMorty.data.remote.RetrofitClient
import com.flatcode.simpleadvancedapps.rickAndMorty.data.remote.apiservices.CharacterApiService
import com.flatcode.simpleadvancedapps.rickAndMorty.data.remote.apiservices.EpisodeApiService
import com.flatcode.simpleadvancedapps.rickAndMorty.data.remote.apiservices.LocationApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofitClient(): RetrofitClient = RetrofitClient()

    @Singleton
    @Provides
    fun provideCharacterApiService(retrofitClient: RetrofitClient): CharacterApiService =
        retrofitClient.provideCharacterApiService()

    @Singleton
    @Provides
    fun provideLocationApiService(retrofitClient: RetrofitClient): LocationApiService =
        retrofitClient.provideLocationApiService()

    @Singleton
    @Provides
    fun provideEpisodeApiService(retrofitClient: RetrofitClient): EpisodeApiService =
        retrofitClient.provideEpisodeApiService()
}
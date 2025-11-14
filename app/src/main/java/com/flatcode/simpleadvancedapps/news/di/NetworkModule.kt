package com.flatcode.simpleadvancedapps.news.di

import com.flatcode.simpleadvancedapps.news.data.remote.RetrofitClient
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
    fun provideRetrofitClient() = RetrofitClient()

    @Singleton
    @Provides
    fun provideNewsApiService(retrofitClient: RetrofitClient) =
        retrofitClient.provideNewsApiService()
}
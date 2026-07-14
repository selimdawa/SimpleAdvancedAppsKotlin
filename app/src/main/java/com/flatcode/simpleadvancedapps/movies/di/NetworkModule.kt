package com.flatcode.simpleadvancedapps.movies.di

import com.flatcode.simpleadvancedapps.movies.data.retrofit.api.ApiService
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
annotation class MovieRetrofit

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @MovieRetrofit
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(DATA.BASE_URL_MOVIES)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(@MovieRetrofit retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }
}
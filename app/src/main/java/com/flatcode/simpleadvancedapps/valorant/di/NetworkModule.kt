package com.flatcode.simpleadvancedapps.valorant.di

import com.flatcode.simpleadvancedapps.Unit.DATA
import com.flatcode.simpleadvancedapps.valorant.data.remote.ValorantService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideValorantService(): ValorantService {
        return Retrofit.Builder()
            .baseUrl(DATA.BASE_URL_VALORANT)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ValorantService::class.java)
    }
}
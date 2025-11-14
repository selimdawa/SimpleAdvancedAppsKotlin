package com.flatcode.simpleadvancedapps.valorant.di

import com.flatcode.simpleadvancedapps.valorant.data.remote.ValorantService
import com.flatcode.simpleadvancedapps.valorant.data.repository.ValorantRepositoryImpl
import com.flatcode.simpleadvancedapps.valorant.domain.repository.ValorantRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideValorantRepository(valorantService: ValorantService): ValorantRepository {
        return ValorantRepositoryImpl(valorantService)
    }
}
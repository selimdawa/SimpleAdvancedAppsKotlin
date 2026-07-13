package com.flatcode.simpleadvancedapps.movies.di

import com.flatcode.simpleadvancedapps.movies.data.room.repository.MoviesRepository
import com.flatcode.simpleadvancedapps.movies.data.room.repository.MoviesRepositoryRealization
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindMoviesRepository(
        moviesRepositoryRealization: MoviesRepositoryRealization
    ): MoviesRepository
}
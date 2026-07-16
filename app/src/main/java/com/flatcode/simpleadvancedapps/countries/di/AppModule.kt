package com.flatcode.simpleadvancedapps.countries.di

import android.content.Context
import com.flatcode.simpleadvancedapps.countries.service.CountryAPI
import com.flatcode.simpleadvancedapps.countries.service.CountryDAO
import com.flatcode.simpleadvancedapps.countries.service.CountryDatabase
import com.flatcode.simpleadvancedapps.countries.utils.CustomDataStore
import com.flatcode.simpleadvancedapps.utils.DATA
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(DATA.BASE_URL_COUNTRY)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideCountryAPI(retrofit: Retrofit): CountryAPI {
        return retrofit.create(CountryAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideCountryDatabase(@ApplicationContext context: Context): CountryDatabase {
        return CountryDatabase(context)
    }

    @Provides
    @Singleton
    fun provideCountryDAO(database: CountryDatabase): CountryDAO {
        return database.countryDao()
    }

    @Provides
    @Singleton
    fun provideCustomDataStore(@ApplicationContext context: Context): CustomDataStore {
        return CustomDataStore(context)
    }
}

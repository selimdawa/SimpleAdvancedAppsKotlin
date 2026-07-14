package com.flatcode.simpleadvancedapps.crypto.di

import com.flatcode.simpleadvancedapps.BuildConfig
import com.flatcode.simpleadvancedapps.crypto.network.CryptoApi
import com.flatcode.simpleadvancedapps.utils.DATA
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return Gson()
    }

    @Singleton
    @Provides
    fun provideHttpLoggerInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        } else {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.NONE
        }
        return httpLoggingInterceptor
    }

    @Singleton
    @Provides
    fun provideHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder().readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS).addInterceptor(httpLoggingInterceptor).build()
    }

    @Singleton
    @Provides
    fun provideConverterFactory(gson: Gson): GsonConverterFactory {
        return GsonConverterFactory.create(gson)
    }

    @Singleton
    @Provides
    fun provideRetrofitInstance(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory,
    ): Retrofit {
        return Retrofit.Builder().baseUrl(DATA.BASE_URL_CRYPTO).client(okHttpClient)
            .addConverterFactory(gsonConverterFactory).build()
    }

    @Singleton
    @Provides
    fun provideApiFactory(retrofit: Retrofit): CryptoApi {
        return retrofit.create(CryptoApi::class.java)
    }
}
package com.flatcode.simpleadvancedapps.crypto.di

import android.content.Context
import android.content.pm.ApplicationInfo
import com.flatcode.simpleadvancedapps.crypto.network.CryptoApi
import com.flatcode.simpleadvancedapps.utils.DATA
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class CryptoRetrofit

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class CryptoOkHttp

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class CryptoGson

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class CryptoInterceptor

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class CryptoConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @CryptoGson
    @Provides
    @Singleton
    fun provideGson(): Gson {
        return Gson()
    }

    @CryptoInterceptor
    @Singleton
    @Provides
    fun provideHttpLoggerInterceptor(@ApplicationContext context: Context): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        val isDebuggable = 0 != context.applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE
        if (isDebuggable) {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        } else {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.NONE
        }
        return httpLoggingInterceptor
    }

    @CryptoOkHttp
    @Singleton
    @Provides
    fun provideHttpClient(@CryptoInterceptor httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder().readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS).addInterceptor(httpLoggingInterceptor).build()
    }

    @CryptoConverterFactory
    @Singleton
    @Provides
    fun provideConverterFactory(@CryptoGson gson: Gson): GsonConverterFactory {
        return GsonConverterFactory.create(gson)
    }

    @CryptoRetrofit
    @Singleton
    @Provides
    fun provideRetrofitInstance(
        @CryptoOkHttp okHttpClient: OkHttpClient,
        @CryptoConverterFactory gsonConverterFactory: GsonConverterFactory,
    ): Retrofit {
        return Retrofit.Builder().baseUrl(DATA.BASE_URL_CRYPTO).client(okHttpClient)
            .addConverterFactory(gsonConverterFactory).build()
    }

    @Singleton
    @Provides
    fun provideApiFactory(@CryptoRetrofit retrofit: Retrofit): CryptoApi {
        return retrofit.create(CryptoApi::class.java)
    }
}
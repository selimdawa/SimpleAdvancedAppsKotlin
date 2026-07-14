package com.flatcode.simpleadvancedapps.news.di

import com.flatcode.simpleadvancedapps.news.data.remote.ApiKeyInterceptor
import com.flatcode.simpleadvancedapps.news.data.remote.NewsApiServices
import com.flatcode.simpleadvancedapps.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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
annotation class NewsRetrofit

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class NewsOkHttp

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class NewsInterceptor

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @NewsInterceptor
    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @NewsOkHttp
    @Provides
    @Singleton
    fun provideOkHttpClient(
        @NewsInterceptor loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(ApiKeyInterceptor())
            .addInterceptor(loggingInterceptor).callTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS).readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS).build()
    }

    @NewsRetrofit
    @Provides
    @Singleton
    fun provideRetrofit(@NewsOkHttp okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder().baseUrl(Constants.BASE_URL_NEWS).client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    @Provides
    @Singleton
    fun provideNewsApiService(@NewsRetrofit retrofit: Retrofit): NewsApiServices {
        return retrofit.create(NewsApiServices::class.java)
    }
}
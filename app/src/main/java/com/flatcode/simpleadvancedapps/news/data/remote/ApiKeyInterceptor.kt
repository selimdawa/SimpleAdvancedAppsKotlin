package com.flatcode.simpleadvancedapps.news.data.remote

import com.flatcode.simpleadvancedapps.utils.DATA
import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalUrl = originalRequest.url
        
        val url = originalUrl.newBuilder()
            .addQueryParameter("apiKey", DATA.API_NEWS)
            .build()
            
        val requestBuilder = originalRequest.newBuilder()
            .url(url)
            
        return chain.proceed(requestBuilder.build())
    }
}

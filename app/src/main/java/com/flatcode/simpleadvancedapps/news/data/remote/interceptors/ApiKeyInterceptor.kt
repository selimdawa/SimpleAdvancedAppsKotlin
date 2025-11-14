package com.flatcode.simpleadvancedapps.news.data.remote.interceptors

import com.flatcode.simpleadvancedapps.Unit.DATA
import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val url = request.url.newBuilder()
            .addQueryParameter("X-Api-Key", DATA.API_NEWS).build()
        request = request.newBuilder().url(url).build()
        return chain.proceed(request)
    }
}
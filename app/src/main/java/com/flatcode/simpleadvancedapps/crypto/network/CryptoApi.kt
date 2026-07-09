package com.flatcode.simpleadvancedapps.crypto.network

import com.flatcode.simpleadvancedapps.utils.DATA
import com.flatcode.simpleadvancedapps.crypto.model.detail.DetailResponse
import com.flatcode.simpleadvancedapps.crypto.model.home.CryptoResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface CryptoApi {

    @GET("v1/cryptocurrency/listings/latest")
    suspend fun getData(
        @Header("X-CMC_PRO_API_KEY") apiKey: String,
        @Query("limit") limit: String,
        @Query("start") start: String
    ): Response<CryptoResponse>

    @GET(DATA.INFO_CRYPTO)
    suspend fun getDetail(
        @Header("X-CMC_PRO_API_KEY") apiKey: String,
        @Query("symbol") symbol: String,
    ): DetailResponse
}
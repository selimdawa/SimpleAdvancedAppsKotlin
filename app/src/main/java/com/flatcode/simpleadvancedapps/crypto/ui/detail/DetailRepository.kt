package com.flatcode.simpleadvancedapps.crypto.ui.detail

import com.flatcode.simpleadvancedapps.crypto.base.BaseRepository
import com.flatcode.simpleadvancedapps.crypto.network.CryptoApi
import javax.inject.Inject

class DetailRepository @Inject constructor(private val apiFactory: CryptoApi) : BaseRepository() {

    suspend fun getDetail(apiKey: String, symbol: String) =
        safeApiRequest { apiFactory.getDetail(apiKey, symbol) }
}
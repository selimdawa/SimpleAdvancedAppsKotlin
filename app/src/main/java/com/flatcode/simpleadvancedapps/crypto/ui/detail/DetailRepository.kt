package com.flatcode.simpleadvancedapps.crypto.ui.detail

import com.flatcode.simpleadvancedapps.crypto.base.BaseRepository
import com.flatcode.simpleadvancedapps.crypto.network.ApiFactory
import javax.inject.Inject

class DetailRepository @Inject constructor(private val apiFactory: ApiFactory) : BaseRepository() {

    suspend fun getDetail(apiKey: String, symbol: String) =
        safeApiRequest { apiFactory.getDetail(apiKey, symbol) }
}
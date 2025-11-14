package com.flatcode.simpleadvancedapps.crypto.ui.home

import com.flatcode.simpleadvancedapps.crypto.base.BaseRepository
import com.flatcode.simpleadvancedapps.crypto.network.ApiFactory
import javax.inject.Inject

class HomeRepository @Inject constructor(private val apiFactory: ApiFactory) : BaseRepository() {

    suspend fun getData(apiKey: String, limit: String) =
        safeApiRequest { apiFactory.getData(apiKey, limit) }
}
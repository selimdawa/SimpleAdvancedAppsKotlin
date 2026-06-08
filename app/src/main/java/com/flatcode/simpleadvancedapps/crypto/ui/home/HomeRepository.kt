package com.flatcode.simpleadvancedapps.crypto.ui.home

import com.flatcode.simpleadvancedapps.crypto.model.home.CryptoResponse
import com.flatcode.simpleadvancedapps.crypto.network.CryptoApi
import com.flatcode.simpleadvancedapps.crypto.utils.NetworkResult
import javax.inject.Inject

class HomeRepository @Inject constructor(private val api: CryptoApi) {

    suspend fun getData(
        apiKey: String, limit: String, start: String
    ): NetworkResult<CryptoResponse> {
        return try {
            val response = api.getData(apiKey, limit, start)
            if (response.isSuccessful && response.body() != null) {
                NetworkResult.Success(response.body()!!)
            } else {
                NetworkResult.Error(false, response.message())
            }
        } catch (e: Exception) {
            NetworkResult.Error(false, e.localizedMessage ?: "An error occurred")
        }
    }
}
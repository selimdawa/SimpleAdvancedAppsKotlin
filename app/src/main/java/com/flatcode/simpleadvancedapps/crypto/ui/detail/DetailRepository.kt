package com.flatcode.simpleadvancedapps.crypto.ui.detail

import com.flatcode.simpleadvancedapps.crypto.base.BaseRepository
import com.flatcode.simpleadvancedapps.crypto.db.dao.CoinDetailDao
import com.flatcode.simpleadvancedapps.crypto.db.entity.CoinDetailEntity
import com.flatcode.simpleadvancedapps.crypto.network.CryptoApi
import javax.inject.Inject

class DetailRepository @Inject constructor(
    private val apiFactory: CryptoApi, private val coinDetailDao: CoinDetailDao
) : BaseRepository() {

    suspend fun getDetailFromApi(apiKey: String, symbol: String) =
        safeApiRequest { apiFactory.getDetail(apiKey, symbol) }

    fun getDetailFromDb(id: Int) = coinDetailDao.getCoinDetail(id)

    suspend fun saveDetailToDb(entity: CoinDetailEntity) = coinDetailDao.insertCoinDetail(entity)
}
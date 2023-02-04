package com.flatcode.simpleadvancedapps.stockMarket.domain.repository

import com.flatcode.simpleadvancedapps.stockMarket.domain.model.CompanyListing
import com.flatcode.simpleadvancedapps.stockMarket.presentation.util.Resource
import kotlinx.coroutines.flow.Flow

interface StockRepository {
    suspend fun getCompanyListings(
        fetchFromRemote: Boolean,
        query: String,
    ): Flow<Resource<List<CompanyListing>>>
}
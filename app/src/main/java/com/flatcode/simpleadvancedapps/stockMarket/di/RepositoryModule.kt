package com.flatcode.simpleadvancedapps.stockMarket.di

import com.flatcode.simpleadvancedapps.stockMarket.data.repository.StockRepositoryImpl
import com.flatcode.simpleadvancedapps.stockMarket.domain.model.CompanyListing
import com.flatcode.simpleadvancedapps.stockMarket.domain.repository.StockRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindCompanyListingsParser(
        companyListingsParser: com.flatcode.simpleadvancedapps.stockMarket.data.csv.CompanyListingsParser,
    ): com.flatcode.simpleadvancedapps.stockMarket.data.csv.CSVParser<CompanyListing>

    @Binds
    @Singleton
    abstract fun bindStockRepository(
        stockRepositoryImpl: StockRepositoryImpl,
    ): StockRepository
}
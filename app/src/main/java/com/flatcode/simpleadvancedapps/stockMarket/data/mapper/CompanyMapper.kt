package com.flatcode.simpleadvancedapps.stockMarket.data.mapper

import com.flatcode.simpleadvancedapps.stockMarket.data.local.CompanyListingEntity
import com.flatcode.simpleadvancedapps.stockMarket.domain.model.CompanyListing

fun CompanyListingEntity.toCompanyListing(): CompanyListing {
    return CompanyListing(
        name = name,
        symbol = symbol,
        exchange = exchange
    )
}

fun CompanyListing.toCompanyListingEntity(): CompanyListingEntity {
    return CompanyListingEntity(
        name = name,
        symbol = symbol,
        exchange = exchange
    )
}
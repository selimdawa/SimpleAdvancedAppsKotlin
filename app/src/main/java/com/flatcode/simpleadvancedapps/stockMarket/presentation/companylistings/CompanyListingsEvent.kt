package com.flatcode.simpleadvancedapps.stockMarket.presentation.companylistings

sealed class CompanyListingsEvent {
    object Refresh: CompanyListingsEvent()
    data class OnSearchQueryChange(val query: String): CompanyListingsEvent()
}

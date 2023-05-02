package com.example.coolproject.feature_market.presentation.company_list

sealed class CompanyListingEvent(){
    object Refresh: CompanyListingEvent()
    data class onSearchQueryChange(val query: String): CompanyListingEvent()
}

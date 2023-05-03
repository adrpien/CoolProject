package com.example.coolproject.feature_market.presentation.company_list

sealed class CompanyListEvent(){
    object Refresh: CompanyListEvent()
    data class onSearchQueryChange(val query: String): CompanyListEvent()
}

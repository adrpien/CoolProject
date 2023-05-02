package com.example.coolproject.feature_market.presentation.company_list

import com.example.coolproject.feature_market.domain.model.Company

data class CompanyListState(
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val companyList: List<Company> = emptyList(),
    val searchQuery: String = ""
)

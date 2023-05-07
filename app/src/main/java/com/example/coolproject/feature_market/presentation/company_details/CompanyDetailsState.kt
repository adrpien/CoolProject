package com.example.coolproject.feature_market.presentation.company_details

import com.example.coolproject.feature_market.domain.model.Company
import com.example.coolproject.feature_market.domain.model.CompanyDetails
import com.example.coolproject.feature_market.domain.model.IntradayInfo

data class CompanyDetailsState(
     val intradayInfoList: List<IntradayInfo> = emptyList(),
    val company: CompanyDetails? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

package com.example.coolproject.feature_market.domain.repository

import com.adrpien.dictionaryapp.core.util.Resource
import com.example.coolproject.feature_market.domain.model.Company
import com.example.coolproject.feature_market.domain.model.CompanyDetails
import com.example.coolproject.feature_market.domain.model.IntradayInfo
import kotlinx.coroutines.flow.Flow

interface MarketRepository {

        suspend fun getCompanyList(
            fetchFromApi: Boolean,
            query: String
        ): Flow<Resource<List<Company>>>

        suspend fun getCompanyDetails(
            symbol: String
        ): Resource<CompanyDetails>

    suspend fun getIntradayInfo(
        symbol: String
    ): Resource<List<IntradayInfo>>
}
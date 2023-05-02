package com.example.coolproject.feature_market.domain.repository

import com.adrpien.dictionaryapp.core.util.Resource
import com.example.coolproject.feature_market.domain.model.Company
import kotlinx.coroutines.flow.Flow

interface MarketRepository {

        suspend fun getCompanyList(
            fetchFromApi: Boolean,
            query: String
        ): Flow<Resource<List<Company>>>

}
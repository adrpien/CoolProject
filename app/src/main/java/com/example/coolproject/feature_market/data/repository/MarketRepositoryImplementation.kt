package com.example.coolproject.feature_market.data.repository

import com.adrpien.dictionaryapp.core.util.Resource
import com.adrpien.dictionaryapp.core.util.ResourceState
import com.example.coolproject.feature_market.data.local.MarketDatabase
import com.example.coolproject.feature_market.data.mappers.toCompany
import com.example.coolproject.feature_market.data.remote.AlphaVantageApi
import com.example.coolproject.feature_market.data.remote.AlphaVantageApi.Companion.API_KEY
import com.example.coolproject.feature_market.domain.model.Company
import com.example.coolproject.feature_market.domain.repository.MarketRepository
import com.opencsv.CSVReader
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import java.io.InputStreamReader
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MarketRepositoryImplementation @Inject constructor(
    val database: MarketDatabase,
    val api: AlphaVantageApi
): MarketRepository {


    override suspend fun getCompanyList(
        fetchFromApi: Boolean,
        query: String,
    ): Flow<Resource<List<Company>>> = flow {
        emit(Resource(ResourceState.LOADING, null, "getCompanyList function starts"))
        val cacheCompanyList = database.dao.getCompanyList(query)
        val isDatabaseEmpty = cacheCompanyList.isEmpty() && query.isBlank()
        val shouldLoadFromCache = !isDatabaseEmpty && !fetchFromApi
        if(shouldLoadFromCache){
            emit(
                Resource(
                    ResourceState.SUCCESS,
                    cacheCompanyList.map { it.toCompany() },
                    "Room data"
                )
            )
        } else {
           val apiCompanyList = try {
               val response = api.getCompanyList()
               val csvReader=  CSVReader(InputStreamReader(response.byteStream()))
           } catch (e: IOException) {
               e.printStackTrace()
               emit(
                   Resource(
                       ResourceState.ERROR,
                       null,
                       "IOException"
                   )
               )
           } catch (e: HttpException) {
               emit(
                   Resource(
                       ResourceState.ERROR,
                       null,
                       "HTTPException"
                   )
               )
           }

        }
    }
}
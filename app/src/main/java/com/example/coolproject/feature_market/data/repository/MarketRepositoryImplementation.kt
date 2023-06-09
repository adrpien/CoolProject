package com.example.coolproject.feature_market.data.repository

import com.adrpien.dictionaryapp.core.util.Resource
import com.adrpien.dictionaryapp.core.util.ResourceState
import com.example.coolproject.feature_market.data.csv.CSVParser
import com.example.coolproject.feature_market.data.local.MarketDatabase
import com.example.coolproject.feature_market.data.mappers.toCompany
import com.example.coolproject.feature_market.data.mappers.toCompanyDetails
import com.example.coolproject.feature_market.data.mappers.toCompanyEntity
import com.example.coolproject.feature_market.data.remote.AlphaVantageApi
import com.example.coolproject.feature_market.data.remote.AlphaVantageApi.Companion.API_KEY
import com.example.coolproject.feature_market.domain.model.Company
import com.example.coolproject.feature_market.domain.model.CompanyDetails
import com.example.coolproject.feature_market.domain.model.IntradayInfo
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
    val api: AlphaVantageApi,
    val companyParser: CSVParser<Company>,
    val intradayInfoParser: CSVParser<IntradayInfo>

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
               val response = api.getCompanyList().byteStream()
                companyParser.parse(response)
           } catch (e: IOException) {
               e.printStackTrace()
               emit(
                   Resource(
                       ResourceState.ERROR,
                       null,
                       "IOException"
                   )
               )
               null
           } catch (e: HttpException) {
               emit(
                   Resource(
                       ResourceState.ERROR,
                       null,
                       "HTTPException"
                   )
               )
               null
           }

            apiCompanyList?.let { companyList ->
                database.dao.clearCompanyList()
                database.dao.insertCompanyEntity(companyList.map { it.toCompanyEntity()  })
                emit(
                    Resource(
                        ResourceState.SUCCESS,
                        database.dao.getCompanyList("").map { it.toCompany() },
                        "IOException"
                    )
                )

            }

        }
    }

    override suspend fun getCompanyDetails(symbol: String): Resource<CompanyDetails> {
        return try {
            val companyDetails = api.getCompanyDetails(symbol = symbol).toCompanyDetails()
            Resource(ResourceState.SUCCESS, companyDetails, "getCompanyDetails succeed")
        } catch (e: IOException){
            e.printStackTrace()
            Resource(
                ResourceState.ERROR,
                null,
                "IOException"
            )
        } catch (e: HttpException) {
            e.printStackTrace()
            Resource(
                ResourceState.ERROR,
                null,
                "HTTPException"
            )
        }
    }

    override suspend fun getIntradayInfo(symbol: String): Resource<List<IntradayInfo>> {
        return try {
            val response = api.getiIntradayInfo(
                symbol = symbol
            )
            val intradayInfoList = intradayInfoParser.parse(response.byteStream())
            Resource(ResourceState.SUCCESS, intradayInfoList, "getIntraDayInfo succeed")
        } catch (e: IOException){
            e.printStackTrace()
                Resource(
                    ResourceState.ERROR,
                    null,
                    "IOException"
                )
        } catch (e: HttpException) {
            e.printStackTrace()
            Resource(
                ResourceState.ERROR,
                null,
                "HTTPException"
            )
        }
    }
}
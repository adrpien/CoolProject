package com.example.coolproject.feature_market.data.remote

import com.example.coolproject.feature_market.data.remote.dto.CompanyDetailsDto
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query

interface AlphaVantageApi {

    // We can use Response body to get access to filestream
    @GET("query?function=LISTING_STATUS")
    suspend fun getCompanyList(
        @Query("apikey")
        apiKey: String = API_KEY,
    ): ResponseBody

    @GET ("query?function=TIME_SERIES_INTRADAY&symbol=IBM&interval=60min&datatype=csv")
    suspend fun getiIntradayInfo(
        @Query("apikey") apiKey: String = API_KEY,
        @Query("symbol") symbol: String
    ): ResponseBody

    @GET("query?function=OVERVIEW")
    suspend fun getCompanyDetails(
        @Query("apikey") apiKey: String = API_KEY,
        @Query("symbol") symbol: String
    ): CompanyDetailsDto

    companion object {
        const val API_KEY = "SUC2WK01D4V2040I"
        const val BASE_URL = "https://www.alphavantage.co/"
    }
}
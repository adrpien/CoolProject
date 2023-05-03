package com.example.coolproject.feature_market.data.remote

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query

interface AlphaVantageApi {

    // We can use Response body to get access to filestream
    @GET("query?function=LISTING_STATUS&apikey={apiKey}")
    suspend fun getCompanyList(
        @Query("apiKey")
        apiKey: String = API_KEY,
    ): ResponseBody

    companion object {
        const val API_KEY = "SUC2WK01D4V2040I"
        const val BASE_URL = "https://www.alphavantage.co/"
    }
}
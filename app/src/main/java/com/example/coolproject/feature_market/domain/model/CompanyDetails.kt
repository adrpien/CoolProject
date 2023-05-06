package com.example.coolproject.feature_market.domain.model

import com.squareup.moshi.Json

data class CompanyDetails(
    val symbol: String,
    val name: String,
    val country: String,
    val industry: String,
    val description: String,
    ) {
}
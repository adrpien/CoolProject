package com.example.coolproject.feature_market.data.mappers

import com.example.coolproject.feature_market.data.local.entities.CompanyEntity
import com.example.coolproject.feature_market.data.remote.dto.CompanyDetailsDto
import com.example.coolproject.feature_market.domain.model.Company
import com.example.coolproject.feature_market.domain.model.CompanyDetails

fun CompanyEntity.toCompany():Company {
    return Company(
        symbol = symbol,
        name = name,
        exchange = exchange
    )
}

fun Company.toCompanyEntity():CompanyEntity {
    return CompanyEntity(
        symbol = symbol,
        name = name,
        exchange = exchange
    )
}

fun CompanyDetailsDto.toCompanyDetails(): CompanyDetails {
    return CompanyDetails(
        symbol = symbol ?: "",
        name = name ?: "",
        country = country ?: "",
        industry = industry ?: "",
        description = description ?: ""
    )
}
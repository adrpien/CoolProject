package com.example.coolproject.feature_market.data.mappers

import com.example.coolproject.feature_market.data.local.entities.CompanyEntity
import com.example.coolproject.feature_market.domain.model.Company

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
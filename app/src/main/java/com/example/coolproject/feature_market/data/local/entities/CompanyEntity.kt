package com.example.coolproject.feature_market.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CompanyEntity(
    val symbol: String,
    val name: String,
    val exchange: String,
    @PrimaryKey
    val id: Int? = null
) {
}
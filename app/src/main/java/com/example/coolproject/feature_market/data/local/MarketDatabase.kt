package com.example.coolproject.feature_market.data.local

import androidx.room.Database
import com.example.coolproject.feature_market.data.local.entities.CompanyEntity

@Database(
    entities = [CompanyEntity::class],
    version = 1
)
abstract class MarketDatabase()
{

    abstract val dao: MarketDao

}
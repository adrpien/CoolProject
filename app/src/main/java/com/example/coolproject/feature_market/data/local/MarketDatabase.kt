package com.example.coolproject.feature_market.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.coolproject.feature_market.data.local.entities.CompanyEntity

@Database(
    entities = [CompanyEntity::class],
    version = 1
)
abstract class MarketDatabase(): RoomDatabase()
{

    abstract val dao: MarketDao

    companion object {
        const val DB_NAME= "market_database"
    }

}
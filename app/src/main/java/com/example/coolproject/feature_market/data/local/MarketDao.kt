package com.example.coolproject.feature_market.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.example.coolproject.feature_market.data.local.entities.CompanyEntity

@Dao
interface MarketDao {

    @Insert(onConflict = REPLACE)
    suspend fun insertCompanyEntity(companyEntityList: List<CompanyEntity>)

    @Query("DELETE FROM companyentity")
    suspend fun clearCompanyList()

    @Query(
        """
        SELECT * 
        FROM companyentity
        WHERE LOWER(name) LIKE '%' || LOWER(:query) || '%' OR
        UPPER(:query) == symbol
        """
    )
    suspend fun getCompanyList(query: String): List<CompanyEntity>

}
package com.example.coolproject.depency_injection

import com.example.coolproject.feature_market.data.csv.CSVParser
import com.example.coolproject.feature_market.data.csv.CSVParserImplementation
import com.example.coolproject.feature_market.data.repository.MarketRepositoryImplementation
import com.example.coolproject.feature_market.domain.model.Company
import com.example.coolproject.feature_market.domain.repository.MarketRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindCompanyListParser(
        csvParserImplementation: CSVParserImplementation
    ): CSVParser<Company>

    @Binds
    @Singleton
    abstract fun bindMarketRepository(
        marketRepositoryImplementation: MarketRepositoryImplementation
    ): MarketRepository
}
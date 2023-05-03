package com.example.coolproject.depency_injection

import android.app.Application
import androidx.room.Room
import com.example.coolproject.feature_market.data.local.MarketDatabase
import com.example.coolproject.feature_market.data.local.MarketDatabase.Companion.DB_NAME
import com.example.coolproject.feature_market.data.remote.AlphaVantageApi
import com.example.coolproject.feature_market.data.remote.AlphaVantageApi.Companion.BASE_URL
import com.example.coolproject.feature_market.data.repository.MarketRepositoryImplementation
import com.example.coolproject.feature_market.domain.repository.MarketRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideAlphaVantageApi(): AlphaVantageApi{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create()) // do i really need this?
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun provideDatabase(app: Application): MarketDatabase {
        return Room.databaseBuilder(
            app,
            MarketDatabase::class.java,
            DB_NAME
        ).build()
    }


}
package com.example.coolproject.feature_market.data.csv

import com.example.coolproject.feature_market.data.mappers.toIntradayInfo
import com.example.coolproject.feature_market.data.remote.dto.IntradayInfoDto
import com.example.coolproject.feature_market.domain.model.CompanyDetails
import com.example.coolproject.feature_market.domain.model.IntradayInfo
import com.opencsv.CSVReader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.io.InputStreamReader
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Inject

class IntradayInfoCsvParser @Inject constructor(): CSVParser<IntradayInfo> {
    override suspend fun parse(stream: InputStream): List<IntradayInfo> {
        val csvReader = CSVReader(InputStreamReader(stream))
        return withContext(Dispatchers.IO) {
            csvReader
                .readAll()
                .drop(1)
                .mapNotNull { row ->
                    val time = row.getOrNull(0)
                    val close = row.getOrNull(4)?.toDouble()
                    IntradayInfoDto(
                        close = close?: return@mapNotNull null,
                        time = time ?: return@mapNotNull null
                    ).toIntradayInfo()
                }
                .filter {
                    it.time.dayOfMonth == LocalDate.now().minusDays(5 ).dayOfMonth
                }
                .sortedBy { it.time.hour }
                .also {
                    csvReader.close()
                }
        }
    }

}
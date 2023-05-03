package com.example.coolproject.feature_market.data.csv

import com.example.coolproject.feature_market.domain.model.Company
import com.opencsv.CSVReader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.io.InputStreamReader
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CSVParserImplementation @Inject constructor(): CSVParser<Company> {

    override suspend fun parse(stream: InputStream): List<Company> {
        val csvReader = CSVReader(InputStreamReader(stream))
        return withContext(Dispatchers.IO) {
            csvReader
                .readAll()
                .drop(1)
                .mapNotNull { row ->
                    val symbol = row.getOrNull(0)
                    val name = row.getOrNull(1)
                    val exchange = row.getOrNull(2)
                    Company(
                        symbol = symbol ?: return@mapNotNull null,
                        name = name?: return@mapNotNull null,
                        exchange = exchange?: return@mapNotNull null
                    )
                }
                .also {
                    csvReader.close()
                }
        }
    }
}
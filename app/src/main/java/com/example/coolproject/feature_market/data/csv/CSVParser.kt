package com.example.coolproject.feature_market.data.csv

import java.io.InputStream

interface CSVParser<T> {

    suspend fun parse(stream: InputStream): List<T>
}